package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.huang.dao.CommentDao;
import com.huang.pojo.Comment;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
@Controller
@CrossOrigin
public class CommentController {

    @Autowired
    CommentDao commentDao;

    @RequestMapping("/getAllComment")
    public String getAll(@RequestParam("videoId") int videoId) {
        return commentDao.getAllCommentByVideoId(videoId);
    }

    @RequestMapping("/insertOne")
    public String insertOne(@RequestParam("msg") String msg,
                            @RequestParam("videoId") int videoId,
                            @RequestParam("userId") int userId,
                            @RequestParam("nickName") String nickName,
                            @RequestParam("imgUrl") String imgUrl,
                            @RequestParam("time") String time) {
        Comment comment = new Comment();
        comment.setMsg(msg);
        comment.setVideoId(videoId);
        comment.setUserId(userId);
        comment.setNickName(nickName);
        comment.setImgUrl(imgUrl);
        comment.setTime(time);
        commentDao.insertOneComment(comment);
        return "OK";
    }
}
