package com.juicemilk.community.controller;

import com.juicemilk.community.dto.NotificationDTO;
import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.mapper.NotificationMapper;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.User;
import com.juicemilk.community.service.NotificationService;
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
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile")
    public String profile(@RequestParam(name="userId") Long id){
        return "redirect:/profile/questions?userId="+id;
    }
    @GetMapping("/profile/{action}")
    public String profileAction(@PathVariable(name="action") String action,
                                Model model,
                                HttpServletRequest request,
                                @RequestParam(name="page",defaultValue = "1") Integer page,
                                @RequestParam(name="size",defaultValue = "8") Integer size,
                                @RequestParam(name="userId") Long id){


        User user=(User)request.getSession().getAttribute("user");
        String t=null;
        if(user==null||!user.getId().equals(id)){
            t="他";
        }else{
            t="我";
        }
        model.addAttribute("t",t);
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName",t+"的提问");
            PageDTO pageDTO=questionService.listByUser(id,page,size);
            model.addAttribute("pageDTO",pageDTO);
            model.addAttribute("userId",id);

        }
        if("replies".equals(action)){
            Long unreadCount=notificationService.unreadCount(id);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("userId",id);
            PageDTO pageDTO=notificationService.listByUser(id,page,size);
            model.addAttribute("pageDTO",pageDTO);
            model.addAttribute("unreadCount",unreadCount);

        }
        if("follow".equals(action)){
            model.addAttribute("section","follow");
            model.addAttribute("sectionName",t+"的关注");
            model.addAttribute("userId",id);

        }
        if("collection".equals(action)){
            model.addAttribute("section","collection");
            model.addAttribute("sectionName",t+"的收藏");
            model.addAttribute("userId",id);

        }
        return"profile";
    }
}
