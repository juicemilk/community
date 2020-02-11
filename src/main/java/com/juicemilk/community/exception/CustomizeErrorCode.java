package com.juicemilk.community.exception;

public enum CustomizeErrorCode implements InCustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不存在，请重新尝试"),
    COMMENT_NOT_FOUND(2002,"回复的评论不存在"),
    TARGET_PARAM_NOT_FOUND(2003,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2004,"请登录后后回复"),
    SYSTEM_ERROR(2005,"服务器异常，请稍后再试"),
    TYPE_PARAM_WRONG(2006,"评论或问题不存在"),
    INVALID_COMMENT(2007,"评论内容不能为空，请重新输入")
;
    private String errorMessage;
    private Integer code;

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code,String errorMessage) {
        this.errorMessage = errorMessage;
        this.code=code;
    }
}
