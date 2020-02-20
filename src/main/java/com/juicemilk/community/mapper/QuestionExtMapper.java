package com.juicemilk.community.mapper;

import com.juicemilk.community.dto.QuestionQueryDTO;
import com.juicemilk.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
    int decComment(Question record);
    List<Question> selectRelated(Question question);

    Object countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
