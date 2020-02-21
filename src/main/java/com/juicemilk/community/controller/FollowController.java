package com.juicemilk.community.controller;

import com.juicemilk.community.mapper.UserFollowMapper;
import com.juicemilk.community.service.QuestionCollectService;
import com.juicemilk.community.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FollowController {

    @Autowired
    private UserFollowService userFollowService;
    @GetMapping("/follow/{action}")
    public String collect(@PathVariable(name="action") String action,
                          @RequestParam(name="idolId") Long idolId,
                          @RequestParam(name="fanId") Long fanId,
                          @RequestParam(name="questionId") Long questionId
    ){
        if(action.equals("confirm")){
            userFollowService.confirmFollow(idolId,fanId);
        }else {
            userFollowService.cancelFollow(idolId,fanId);
        }
        return "redirect:/question/"+questionId;
    }
}
