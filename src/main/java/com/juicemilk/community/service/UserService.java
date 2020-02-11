package com.juicemilk.community.service;

import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.User;
import com.juicemilk.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers=userMapper.selectByExample(userExample);
        if(dbUsers.size()==0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
//            User dbUser=dbUsers.get(0);
//            User updateUser=new User();
//            updateUser.setGmtModified(System.currentTimeMillis());
//            updateUser.setLogin(user.getLogin());
//            updateUser.setName(dbUser.getName());
//            updateUser.setAvatarUrl(user.getAvatarUrl());
//            updateUser.setToken(user.getToken());
            user.setGmtModified(System.currentTimeMillis());
            UserExample example=new UserExample();
            example.createCriteria().andAccountIdEqualTo(user.getAccountId());
            userMapper.updateByExampleSelective(user,example);
        }
    }
}
