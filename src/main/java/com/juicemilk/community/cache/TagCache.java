package com.juicemilk.community.cache;

import com.juicemilk.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOList=new ArrayList<>();
        TagDTO programName = new TagDTO();
        programName.setCategoryName("开发语言");
        programName.setTags(Arrays.asList("Python","Js","Java","C++"));
        tagDTOList.add(programName);

        TagDTO FrameWork = new TagDTO();
        FrameWork.setCategoryName("平台架构");
        FrameWork.setTags(Arrays.asList("Spring","Django","Struts","Yii"));
        tagDTOList.add(FrameWork);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Tomcat","Docker","Centos","Hadoop"));
        tagDTOList.add(server);
        return tagDTOList;
    }

    public static String filterInvalid(String tags){
        String tempTags=StringUtils.replace(tags,"，",",");
        String[] split= StringUtils.split(tempTags,",");
        List<TagDTO> tagDTOList=get();
        List<String> tagList=tagDTOList.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid=Arrays.stream(split).filter(t ->!tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
