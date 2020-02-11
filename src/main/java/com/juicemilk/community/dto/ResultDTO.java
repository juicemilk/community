package com.juicemilk.community.dto;

import com.juicemilk.community.exception.CustomizeErrorCode;
import com.juicemilk.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO error0f(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO error0f(CustomizeErrorCode errorCode){
        return error0f(errorCode.getCode(),errorCode.getErrorMessage());
    }

    public static ResultDTO error0f(CustomizeException e){
        return error0f(e.getCode(),e.getErrorMessage());
    }
    public static ResultDTO ok0f(){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
