package com.juicemilk.community.exception;

public class CustomizeException extends RuntimeException {
    private String errorMessage;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public CustomizeException(InCustomizeErrorCode errorCode) {
        this.code=errorCode.getCode();
        this.errorMessage=errorCode.getErrorMessage();
    }
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
