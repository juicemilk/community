package com.juicemilk.community.mapper;

import com.juicemilk.community.model.Question;
public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
}
