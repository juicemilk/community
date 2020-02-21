package com.juicemilk.community.service;

import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.dto.QuestionDTO;
import com.juicemilk.community.mapper.QuestionCollectMapper;
import com.juicemilk.community.mapper.QuestionExtMapper;
import com.juicemilk.community.mapper.QuestionMapper;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionCollectService {
    @Autowired
    private QuestionCollectMapper questionCollectMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PageDTO listByUser(Long userId, Integer page, Integer size){
        PageDTO<QuestionDTO> pageDTO=new PageDTO();
        QuestionCollectExample questionCollectExample=new QuestionCollectExample();
        questionCollectExample.createCriteria().andUserIdEqualTo(userId);
        List<QuestionCollect> questionCollectList=questionCollectMapper.selectByExample(questionCollectExample);
        List<Long> questionIdList=questionCollectList.stream().map(q -> q.getQuestionId()).collect(Collectors.toList());
        QuestionExample questionExample=new QuestionExample();
        Integer totalCount=0;
        if(questionIdList.size()!=0){

            questionExample.createCriteria().andIdIn(questionIdList);
            questionExample.setOrderByClause("gmt_modified desc");
            totalCount=(int)questionMapper.countByExample(questionExample);
        }else{
            questionExample.createCriteria().andIdEqualTo(-1l);
        }

        pageDTO.setPagination(totalCount,page,size);
        page=pageDTO.getPage();
        Integer offset=size*(page-1);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questionList) {
            UserExample userExample=new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        pageDTO.setDataList(questionDTOList);

        return pageDTO;
    }

    public List<User> listByQuestion(Long questionId){
        QuestionCollectExample questionCollectExample=new QuestionCollectExample();
        questionCollectExample.createCriteria().andQuestionIdEqualTo(questionId);
        List<QuestionCollect> questionCollectList=questionCollectMapper.selectByExample(questionCollectExample);
        List<Long> userIdList=questionCollectList.stream().map(q -> q.getUserId()).collect(Collectors.toList());
        UserExample userExample=new UserExample();
        userExample.createCriteria().andIdIn(userIdList);
        List<User> userList=userMapper.selectByExample(userExample);
        return userList;
    }

    public void confirmCollect(Long questionId,Long userId){
        QuestionCollectExample questionCollectExample=new QuestionCollectExample();
        questionCollectExample.createCriteria().andQuestionIdEqualTo(questionId).andUserIdEqualTo(userId);
        List<QuestionCollect> questionCollectList=questionCollectMapper.selectByExample(questionCollectExample);
        if(questionCollectList.size()==0){
            QuestionCollect questionCollect=new QuestionCollect();
            questionCollect.setQuestionId(questionId);
            questionCollect.setUserId(userId);
            questionCollect.setGmtCreate(System.currentTimeMillis());
            questionCollectMapper.insert(questionCollect);
            Question question=questionMapper.selectByPrimaryKey(questionId);
            question.setCollectCount(1);
            questionExtMapper.incCollect(question);
        }
    }
    public void cancelCollect(Long questionId,Long userId){
        QuestionCollectExample questionCollectExample=new QuestionCollectExample();
        questionCollectExample.createCriteria().andQuestionIdEqualTo(questionId).andUserIdEqualTo(userId);
        questionCollectMapper.deleteByExample(questionCollectExample);
        Question question=questionMapper.selectByPrimaryKey(questionId);
        question.setCollectCount(1);
        questionExtMapper.decCollect(question);
    }

    public boolean collectStatus(Long questionId,Long userId){
        QuestionCollectExample questionCollectExample=new QuestionCollectExample();
        questionCollectExample.createCriteria().andQuestionIdEqualTo(questionId).andUserIdEqualTo(userId);
        List<QuestionCollect> questionCollectList=questionCollectMapper.selectByExample(questionCollectExample);
        if(questionCollectList.size()==0){
            return false;
        }else{
            return true;
        }
    }
}
