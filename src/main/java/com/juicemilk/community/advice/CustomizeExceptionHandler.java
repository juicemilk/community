package com.juicemilk.community.advice;

import com.alibaba.fastjson.JSON;
import com.juicemilk.community.dto.ResultDTO;
import com.juicemilk.community.exception.CustomizeErrorCode;
import com.juicemilk.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.IIOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
//@ResponseBody
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                  Throwable ex,
                  Model model,
                  HttpServletResponse response){
        HttpStatus status=getStatus(request);
        String contentType=request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO;
            if(ex instanceof CustomizeException){
                resultDTO=ResultDTO.error0f((CustomizeException) ex);
            }else{
                resultDTO=ResultDTO.error0f(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer=response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ioe){
            }
            return null;
        }else{
            if(ex instanceof CustomizeException){
                model.addAttribute("errorMessage",((CustomizeException) ex).getErrorMessage());
            }else{
                model.addAttribute("errorMessage",CustomizeErrorCode.SYSTEM_ERROR.getErrorMessage());
            }
            return new ModelAndView("error");
        }

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode=(Integer)request.getAttribute("javax.servlet.error.status_code");
        if(statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
