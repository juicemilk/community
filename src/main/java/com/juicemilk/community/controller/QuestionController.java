package com.juicemilk.community.controller;

import com.juicemilk.community.dto.CommentDTO;
import com.juicemilk.community.dto.QuestionDTO;
import com.juicemilk.community.enums.CommentTypeEnum;
import com.juicemilk.community.service.CommentService;
import com.juicemilk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String getQuestion(@PathVariable(name="id") Long id,
                              Model model){
        QuestionDTO questionDTO=questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> commentDTOList=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOList",commentDTOList);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }

    @GetMapping("/question")
    public String getQuestionToIndex(){
        return "redirect:/";

    }
}
