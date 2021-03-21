package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.huang.dao.DiscussDao;
import com.huang.pojo.Answer;
import com.huang.pojo.Discuss;
import com.huang.service.SearchService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@ResponseBody
@Controller
public class DiscussController {
    @Autowired
    DiscussDao discussDao;
    @Autowired
    SearchService searchService;

    @RequestMapping("/getAllDiscuss")
    public String getAll() {
        return discussDao.getAllDiscuss();
    }

    @RequestMapping("/getById")
    public String getByUserId(@RequestParam("userId") int userId) {
        return discussDao.getDiscussByUserId(userId);
    }

    @RequestMapping("/addOneViewOfDisscuss")
    public String getOne(@RequestParam("discussId") int discussId) {

        return discussDao.addOneViewOfDiscuss(discussId);
    }

    @RequestMapping("/insertDiscuss")
    public String insertDiscuss(@RequestParam("userId") int userId,
                                @RequestParam("name") String name,
                                @RequestParam("des") String des,
                                @RequestParam("imgUrl") String imgUrl
    ) throws IOException {
        Discuss discuss = new Discuss();
        discuss.setUserId(userId);
        discuss.setDes(des);
        discuss.setImgUrl(imgUrl);
        discuss.setTitle(name);
        discuss.setViews(0);
        discuss.setAnswer(0);
        discussDao.insertDiscuss(discuss);
        searchService.insertDiscuss(discuss);
        return "OK";
    }

//    @RequestMapping("/selectSomeDiscuss")
//    public String selectSome(@RequestParam("keyWord") String keyWord) {
//        SqlSession sqlSession = Mybatis.getSqlSession();
//        discussDao mapper = sqlSession.getMapper(discussDao.class);
//        List<discuss> ans = mapper.selectSome(keyWord);
//        return JSON.toJSONString(ans);
//    }

    @RequestMapping("/getAllAnswer")
    public String getAllAnswer(@RequestParam("userId") int discussId) {
        return discussDao.getAnswerByDiscussId(discussId);

    }

    @RequestMapping("/insertOneAnswer")
    public String insertOneAnswer(@RequestParam("discussId") int discussId,
                                  @RequestParam("answer") String answer,
                                  @RequestParam("userId") int userId,
                                  @RequestParam("nickName") String nickName,
                                  @RequestParam("time") String time,
                                  @RequestParam("headImg") String headImg) {
        Answer an = new Answer();
        an.setDiscussId(discussId);
        an.setAnswer(answer);
        an.setUserId(userId);
        an.setAgree(0);
        an.setNickName(nickName);
        an.setTime(time);
        an.setHeadImg(headImg);
        discussDao.insertAnswer(an);
        return "OK";
    }

}
