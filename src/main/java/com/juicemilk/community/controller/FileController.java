package com.juicemilk.community.controller;

import com.juicemilk.community.dto.FileDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class FileController {
    @RequestMapping("/file/upload")
   @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)(request);
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        String[] temp= StringUtils.split(file.getOriginalFilename(),'.');
        String type=temp[temp.length-1];
//        String basePath = request.getSession().getServletContext().getRealPath("/");
        String basePath="F:/IntelliJWorkSpace/community/src/main/resources/static/images/uploadImages/";
        String trueFileName = String.valueOf(System.currentTimeMillis()) + "." + type;
        File directory = new File(basePath);
        FileDTO fileDTO=new FileDTO();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            file.transferTo(new File(basePath + trueFileName));
            fileDTO.setSuccess(1);
//            fileDTO.setMessage();
            fileDTO.setUrl("/images/uploadImages/"+trueFileName);
        } catch (Exception e) {
            // TODO
            fileDTO.setSuccess(0);
        }


        return fileDTO;
    }
}
