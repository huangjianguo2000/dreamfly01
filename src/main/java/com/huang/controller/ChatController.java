package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.huang.dao.ChatMsgDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@ResponseBody
@Controller
public class ChatController {

    @Autowired
    ChatMsgDao chatMsgDao;

    @RequestMapping("/getChatMsg")
    public String getMsg(@RequestParam("me") int me,
                         @RequestParam("you") int you) {
        return chatMsgDao.getAllChatMsg(me, you);
    }

    @RequestMapping("/insertMsg")
    public String insertMsg(@RequestParam("me") int me,
                            @RequestParam("you") int you,
                            @RequestParam("msg") String msg,
                            @RequestParam("time") String time) {
        chatMsgDao.insertMessage(me,you,msg,time);
        return "OK";
    }

    //找到所有发给自己的没有读消息
    @RequestMapping("/searchMsg")
    public String searchMsg(@RequestParam("id") int id) {
        return chatMsgDao.getNoReadMessage(id);
    }


}
