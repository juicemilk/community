package com.juicemilk.community.controller;

import com.juicemilk.community.service.CommentService;
import com.juicemilk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DelectController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/delect/{action}")
    public String delect(@PathVariable(name="action") String action,
                         @RequestParam(name="id") Long id){
        if(action.equals("question")){
            questionService.delectQuestion(id);
            return "redirect:/";
        }
        if(action.equals("comment")){
            Long questionId=commentService.delectComment(id);

            return "redirect:/question/"+questionId;
        }
        return null;

    }

}
