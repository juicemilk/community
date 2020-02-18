package com.juicemilk.community.controller;

import com.juicemilk.community.dto.NotificationDTO;
import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.enums.NotificationStatusEnum;
import com.juicemilk.community.model.Notification;
import com.juicemilk.community.model.User;
import com.juicemilk.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profileAction(@PathVariable(name="id") Long id,
                                HttpServletRequest request
                                ){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
         Notification notification=notificationService.read(id,user);
        if(notification!=null){
            Long questionId=notification.getQuestionId();
            return "redirect:/question/"+questionId;
        }else{
            return "redirect:/";
        }

    }
}
