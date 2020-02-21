package com.juicemilk.community.controller;

import com.juicemilk.community.service.QuestionCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CollectController {

    @Autowired
    private QuestionCollectService questionCollectService;
    @GetMapping("/collect/{action}")
    public String collect(@PathVariable(name="action") String action,
                          @RequestParam(name="questionId") Long questionId,
                          @RequestParam(name="userId") Long userId
                          ){
        if(action.equals("confirm")){
            questionCollectService.confirmCollect(questionId,userId);
        }else {
            questionCollectService.cancelCollect(questionId,userId);
        }
        return "redirect:/question/"+questionId;
    }
}
