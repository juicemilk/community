package com.juicemilk.community.service;

import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.dto.QuestionDTO;
import com.juicemilk.community.exception.CustomizeErrorCode;
import com.juicemilk.community.exception.CustomizeException;
import com.juicemilk.community.mapper.QuestionExtMapper;
import com.juicemilk.community.mapper.QuestionMapper;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.Question;
import com.juicemilk.community.model.QuestionExample;
import com.juicemilk.community.model.User;
import com.juicemilk.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void incView(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO=new PageDTO();
        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());
        pageDTO.setPagination(totalCount,page,size);
        page=pageDTO.getPage();
        Integer offset=size*(page-1);
        QuestionExample questionExample=new QuestionExample();
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
        pageDTO.setQuestionDTOList(questionDTOList);

        return pageDTO;
    }

    public PageDTO listByUser(Long id,Integer page,Integer size){
        PageDTO pageDTO=new PageDTO();
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        Integer totalCount=(int)questionMapper.countByExample(questionExample);
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
        pageDTO.setQuestionDTOList(questionDTOList);

        return pageDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        UserExample userExample=new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        List<User> users = userMapper.selectByExample(userExample);
        questionDTO.setUser(users.get(0));
        return questionDTO;

    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setLikeCount(0);
            question.setViewCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample=new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updatedCode=questionMapper.updateByExampleSelective(question,questionExample);
            if(updatedCode!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
