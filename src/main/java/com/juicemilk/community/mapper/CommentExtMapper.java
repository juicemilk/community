package com.juicemilk.community.mapper;

import com.juicemilk.community.model.Comment;
public interface CommentExtMapper {
    int incComment(Comment comment);
    int decComment(Comment comment);
}
