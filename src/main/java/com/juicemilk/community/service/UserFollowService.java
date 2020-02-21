package com.juicemilk.community.service;

import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.dto.QuestionDTO;
import com.juicemilk.community.mapper.*;
import com.juicemilk.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFollowService {
    @Autowired
    private UserFollowMapper userFollowMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;


    public PageDTO listByFan(Long fanId,Integer page,Integer size){
        UserFollowExample userFollowExample=new UserFollowExample();
        userFollowExample.createCriteria().andFanEqualTo(fanId);
        List<UserFollow> userFollowList=userFollowMapper.selectByExample(userFollowExample);
        List<Long> idolId=userFollowList.stream().map(q -> q.getIdol()).collect(Collectors.toList());
        return getPageDTO(page, size, idolId);
    }

    //
    public PageDTO listByIdol(Long idolId,Integer page,Integer size){
        UserFollowExample userFollowExample=new UserFollowExample();
        userFollowExample.createCriteria().andIdolEqualTo(idolId);
        List<UserFollow> userFollowList=userFollowMapper.selectByExample(userFollowExample);
        List<Long> fanId=userFollowList.stream().map(q -> q.getIdol()).collect(Collectors.toList());
        return getPageDTO(page, size, fanId);
    }

    //得到PageDTO
    private PageDTO getPageDTO(Integer page, Integer size, List<Long> idolId) {
        PageDTO<User> pageDTO=new PageDTO();
        UserExample userExample=new UserExample();
        Integer totalCount=0;
        if(idolId.size()!=0){

            userExample.createCriteria().andIdIn(idolId);
            userExample.setOrderByClause("gmt_create desc");
            totalCount=(int)userMapper.countByExample(userExample);
        }else{
            userExample.createCriteria().andIdEqualTo(-1l);
        }

        pageDTO.setPagination(totalCount,page,size);
        page=pageDTO.getPage();
        Integer offset=size*(page-1);
        List<User> userList = userMapper.selectByExampleWithRowbounds(userExample,new RowBounds(offset,size));
        pageDTO.setDataList(userList);
        return pageDTO;
    }

    public void confirmFollow(Long idolId,Long fanId){
        UserFollowExample userFollowExample=new UserFollowExample();
        userFollowExample.createCriteria().andIdolEqualTo(idolId).andFanEqualTo(fanId);
        List<UserFollow> userFollowList=userFollowMapper.selectByExample(userFollowExample);
        if(userFollowList.size()==0){
            UserFollow userFollow=new UserFollow();
            userFollow.setIdol(idolId);
            userFollow.setFan(fanId);
            userFollow.setGmtCreate(System.currentTimeMillis());
            userFollowMapper.insert(userFollow);
            User idolUser=userMapper.selectByPrimaryKey(idolId);
            User fanUser=userMapper.selectByPrimaryKey(fanId);
            idolUser.setFanCount(1);
            fanUser.setFollowCount(1);
            userExtMapper.incFan(idolUser);
            userExtMapper.incFollow(fanUser);
        }
    }
    public void cancelFollow(Long idolId,Long fanId){
        UserFollowExample userFollowExample=new UserFollowExample();
        userFollowExample.createCriteria().andIdolEqualTo(idolId).andFanEqualTo(fanId);
        userFollowMapper.deleteByExample(userFollowExample);
        User idolUser=userMapper.selectByPrimaryKey(idolId);
        User fanUser=userMapper.selectByPrimaryKey(fanId);
        idolUser.setFanCount(1);
        fanUser.setFollowCount(1);
        userExtMapper.decFan(idolUser);
        userExtMapper.decFollow(fanUser);
    }

    public boolean followStatus(Long idolId,Long fanId){
        UserFollowExample userFollowExample=new UserFollowExample();
        userFollowExample.createCriteria().andIdolEqualTo(idolId).andFanEqualTo(fanId);
        List<UserFollow> userFollowList=userFollowMapper.selectByExample(userFollowExample);
        if(userFollowList.size()==0){
            return false;
        }else{
            return true;
        }
    }
}
