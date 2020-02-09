package com.juicemilk.community.controller;

import com.juicemilk.community.dto.AccessTokenDto;
import com.juicemilk.community.dto.GithubUser;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.User;
import com.juicemilk.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.Redirect.uri}")
    private String RedirectUri;

    @Autowired
    private UserMapper usermapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(RedirectUri);
        accessTokenDto.setState(state);
        String accessToken= githubProvider.gteAccessToken(accessTokenDto);
        System.out.println(accessToken);
        GithubUser githubuser = githubProvider.getUser(accessToken);
        User user=new User();
        if(githubuser!=null&&githubuser.getId()!=null){
            if(usermapper.findByAccountId(String.valueOf(githubuser.getId()))==null){
                user.setToken(UUID.randomUUID().toString());
                user.setName(githubuser.getName());
                user.setAccountId(String.valueOf(githubuser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setLogin(githubuser.getLogin());
                user.setAvatarUrl(githubuser.getAvatar_url());
                usermapper.insert(user);
            }else{
                user = usermapper.findByAccountId(String.valueOf(githubuser.getId()));
                usermapper.updateModified(System.currentTimeMillis(),user.getAccountId());
            }

            String token=user.getToken();
            response.addCookie(new Cookie("token",token));
            //登录成功，写cookie和session
//            request.getSession().setAttribute("user",githubuser);
            return "redirect:/";
        }else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
