package com.juicemilk.community.service;

import com.juicemilk.community.dto.CommentDTO;
import com.juicemilk.community.enums.CommentTypeEnum;
import com.juicemilk.community.enums.NotificationStatusEnum;
import com.juicemilk.community.enums.NotificationTypeEnum;
import com.juicemilk.community.exception.CustomizeErrorCode;
import com.juicemilk.community.exception.CustomizeException;
import com.juicemilk.community.mapper.*;
import com.juicemilk.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void insert(Comment comment, User commentator) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            Comment dbComment=commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question=questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incComment(parentComment);
            Long receiver=dbComment.getCommentator();
            createNotify(comment, receiver,commentator.getName(),dbComment.getContent(),question.getId(),NotificationTypeEnum.RePLY_COMMENT);
        }else{
            Question question=questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
            Long receiver=question.getCreator();
            createNotify(comment, receiver,commentator.getName(),question.getTitle(),question.getId(),NotificationTypeEnum.RePLY_QUESTION);

        }
    }

//创建通知
    private void  createNotify(Comment comment, Long receiver, String commentatorName,String title,Long parentId,NotificationTypeEnum notificationType) {
        if(comment.getCommentator().equals(receiver)){
            return;
        }
        Notification notification=new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(comment.getParentId());
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setQuestionId(parentId);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setNotifierName(commentatorName);
        notification.setOuterTitle(title);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample=new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_modified desc");
        List<Comment> commentList=commentMapper.selectByExample(commentExample);
        if(commentList.size()==0){
            return new ArrayList<>();
        }
        Set<Long> commentators=commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds=new ArrayList<>();
        userIds.addAll(commentators);
        UserExample userExample=new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> userList=userMapper.selectByExample(userExample);
        Map<Long,User> userMap=userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOList=commentList.stream().map(comment -> {CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
        return commentDTO;}).collect(Collectors.toList());
        return  commentDTOList;
    }
}
