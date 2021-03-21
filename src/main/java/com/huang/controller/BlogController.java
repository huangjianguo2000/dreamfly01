package com.huang.controller;

import com.alibaba.fastjson.JSON;
import com.huang.dao.BlogDao;
import com.huang.pojo.Blog;
import com.huang.service.SearchService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
@CrossOrigin
public class BlogController {

    @Autowired
    BlogDao blogDao;
    @Autowired
    SearchService searchService;

    @RequestMapping("/getBlogAll")
    public String getBlogAll() {
        return blogDao.getAllBlog();
    }

    @RequestMapping("/getBlogByUserId")
    public String getBlogByUserId(@RequestParam("userId") int userId) {
        return blogDao.getBlogByUserId(userId);
    }

//    @RequestMapping("/searchBlog")
//    public String searchBlog(@RequestParam("keyWord") String keyWord) {
//        SqlSession sqlSession = Mybatis.getSqlSession();
//        blogDao mapper = sqlSession.getMapper(blogDao.class);
//        List<blog> ans = mapper.getBlogList2(keyWord);
//        sqlSession.close();
//        return JSON.toJSONString(ans);
//    }

    @RequestMapping("/searchBlogById")
    public String searchBlogById(@RequestParam("id") int id) {
        return blogDao.searchBlogById(id);
    }

    @RequestMapping("/insertBlog")
    public String insertBlog(@RequestBody Map map) throws IOException {

        String title = (String) map.get("title");
        String text = (String) map.get("text");
        int userId = (int) map.get("userId");
        String time = (String) map.get("time");
        String imgUrl = (String) map.get("imgUrl");
        Blog blog = new Blog();
        blog.setImgUrl(imgUrl);
        blog.setTitle(title);
        blog.setText(text);
        blog.setTime(time);
        blog.setUserId(userId);
        blog.setBrowseNum(0);
        blog.setCollectionNum(0);
        blog.setUpNum(0);
        blogDao.insertBlog(blog);
        searchService.insertBlog(blog);
        return "OK";
    }
}
