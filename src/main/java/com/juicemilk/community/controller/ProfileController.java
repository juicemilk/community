package com.juicemilk.community.controller;

import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.User;
import com.juicemilk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile")
    public String profile(){
        return "redirect:/profile/questions";
    }
    @GetMapping("/profile/{action}")
    public String profileAction(@PathVariable(name="action") String action,
                                Model model,
                                HttpServletRequest request,
                                @RequestParam(name="page",defaultValue = "1") Integer page,
                                @RequestParam(name="size",defaultValue = "8") Integer size){

        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageDTO pageDTO=questionService.listByUser(user.getId(),page,size);
            model.addAttribute("pageDTO",pageDTO);
        }
        if("reply".equals(action)){
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","最新回复");
//            PageDTO pageDTO=questionService.listByUser(user.getAccountId(),page,size);
//            model.addAttribute("pageDTO",pageDTO);
        }
        if("follow".equals(action)){
            model.addAttribute("section","follow");
            model.addAttribute("sectionName","我的关注");
        }
        if("collection".equals(action)){
            model.addAttribute("section","collection");
            model.addAttribute("sectionName","我的收藏");
        }
        return"profile";
    }
}
