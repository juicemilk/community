package com.juicemilk.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private String creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;

}
