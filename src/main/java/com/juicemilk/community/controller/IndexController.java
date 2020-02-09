package com.juicemilk.community.controller;

import com.juicemilk.community.dto.QuestionDTO;
import com.juicemilk.community.mapper.QuestionMapper;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.Question;
import com.juicemilk.community.model.User;
import com.juicemilk.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        request.getSession().setAttribute("user",null);
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList=questionService.list();
        model.addAttribute("questionlist",questionList);
        return "index";
    }

    @GetMapping("/logout")
    public String loginOut(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token=null;
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                token=cookie.getValue();
                System.out.println(token);
                break;
            }
        }
        String newToken= UUID.randomUUID().toString();
        userMapper.updateToken(token,newToken);
        return "redirect:/";
    }
}
