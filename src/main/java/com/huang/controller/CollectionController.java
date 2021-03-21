package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.huang.dao.CollectionDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@ResponseBody
@CrossOrigin
public class CollectionController {
    @Autowired
    CollectionDao collectionDao;
    @RequestMapping("/getAll")
    public String getAll(@RequestParam("userId") int userId) {
        return collectionDao.getAllByUserId(userId);
    }

    @RequestMapping("/insertCollection")
    public String insertCollection(@RequestParam("userId") int userId, @RequestParam("videoId") int videoId) {
        collectionDao.insertBuyed(userId, videoId);
        return "OK";
    }
}
