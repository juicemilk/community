package com.juicemilk.community.controller;

import com.juicemilk.community.dto.CommentDTO;
import com.juicemilk.community.dto.ResultDTO;
import com.juicemilk.community.exception.CustomizeErrorCode;
import com.juicemilk.community.mapper.CommentMapper;
import com.juicemilk.community.model.Comment;
import com.juicemilk.community.model.User;
import com.juicemilk.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value="/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.error0f(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentDTO.getContent()==""){
            return ResultDTO.error0f(CustomizeErrorCode.INVALID_COMMENT);
        }
        Comment comment=new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.ok0f();
    }
}
