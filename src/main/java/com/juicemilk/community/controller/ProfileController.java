package com.juicemilk.community.controller;

import com.juicemilk.community.dto.NotificationDTO;
import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.mapper.NotificationMapper;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.User;
import com.juicemilk.community.service.NotificationService;
import com.juicemilk.community.service.QuestionCollectService;
import com.juicemilk.community.service.QuestionService;
import com.juicemilk.community.service.UserFollowService;
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
    @Autowired
    private QuestionCollectService questionCollectService;
    @Autowired
    private UserFollowService userFollowService;
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
            model.addAttribute("repliesname",t+"的回复");
        }else{
            t="我";
            model.addAttribute("repliesname","最新回复");
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
            if(t.equals("我")){
                Long unreadCount=notificationService.unreadCount(id);
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","最新回复");
                model.addAttribute("userId",id);
                PageDTO pageDTO=notificationService.listByUser(id,page,size);
                model.addAttribute("pageDTO",pageDTO);
                model.addAttribute("unreadCount",unreadCount);
            }else{
                Long unreadCount=0L;
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","他的回复");
                model.addAttribute("userId",id);
                PageDTO pageDTO=notificationService.listByOtherUser(id,page,size);
                model.addAttribute("pageDTO",pageDTO);
                model.addAttribute("unreadCount",unreadCount);
            }

        }
        if("follow".equals(action)){
            PageDTO pageDTO=userFollowService.listByFan(id,page,size);
            model.addAttribute("section","follow");
            model.addAttribute("sectionName",t+"的关注");
            model.addAttribute("userId",id);
            model.addAttribute("pageDTO",pageDTO);
        }
        if("collect".equals(action)){
            PageDTO pageDTO=questionCollectService.listByUser(id,page,size);
            model.addAttribute("section","collect");
            model.addAttribute("sectionName",t+"的收藏");
            model.addAttribute("userId",id);
            model.addAttribute("pageDTO",pageDTO);

        }
        return"profile";
    }
}
