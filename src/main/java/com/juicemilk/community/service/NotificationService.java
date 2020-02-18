package com.juicemilk.community.service;

import com.juicemilk.community.dto.NotificationDTO;
import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.dto.QuestionDTO;
import com.juicemilk.community.enums.NotificationStatusEnum;
import com.juicemilk.community.enums.NotificationTypeEnum;
import com.juicemilk.community.exception.CustomizeErrorCode;
import com.juicemilk.community.exception.CustomizeException;
import com.juicemilk.community.mapper.NotificationMapper;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO listByUser(Long id, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO=new PageDTO();
        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        notificationExample.setOrderByClause("gmt_create desc");
        Integer totalCount=(int)notificationMapper.countByExample(notificationExample);
        pageDTO.setPagination(totalCount,page,size);
        page=pageDTO.getPage();
        Integer offset=size*(page-1);
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(offset,size));
        if(notificationList.size()==0){
            return pageDTO;
        }
        List<NotificationDTO> notificationDTOList=new ArrayList<>();
        for(Notification notification:notificationList){
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setReplyType(NotificationTypeEnum.nameOfType(notification.getType()));
            User notifier=userMapper.selectByPrimaryKey(notification.getNotifier());
            notificationDTO.setNotifierUser(notifier);
            notificationDTOList.add(notificationDTO);
        }

        pageDTO.setDataList(notificationDTOList);

        return pageDTO;
    }

    public Long unreadCount(Long id){
        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        Long totalCount=(Long)notificationMapper.countByExample(notificationExample);
        return totalCount;
    }

    public Notification read(Long id, User user) {
        Notification notification=notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(notification.getStatus()== NotificationStatusEnum.UNREAD.getStatus()){
            notification.setStatus(NotificationStatusEnum.READ.getStatus());
            notificationMapper.updateByPrimaryKey(notification);
        }
        return notification;
    }
}
