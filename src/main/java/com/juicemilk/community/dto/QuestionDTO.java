package com.juicemilk.community.dto;

import com.juicemilk.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}
