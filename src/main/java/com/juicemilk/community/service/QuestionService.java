package com.juicemilk.community.service;

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

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
