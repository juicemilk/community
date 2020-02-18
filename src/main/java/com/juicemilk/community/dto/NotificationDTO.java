package com.juicemilk.community.dto;

import com.juicemilk.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private User notifierUser;
    private String outerTitle;
    private String replyType;
    private Long questionId;
}
