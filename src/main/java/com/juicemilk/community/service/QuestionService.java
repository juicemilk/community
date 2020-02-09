package com.juicemilk.community.service;

import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.dto.QuestionDTO;
import com.juicemilk.community.mapper.QuestionMapper;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.Question;
import com.juicemilk.community.model.User;
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

    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO=new PageDTO();
        Integer totalCount=questionMapper.getQuestionNum();
        pageDTO.setPagination(totalCount,page,size);
        page=pageDTO.getPage();
        Integer offset=size*(page-1);
        List<Question> questionList = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOList);

        return pageDTO;
    }
}
