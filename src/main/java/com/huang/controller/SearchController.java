package com.huang.controller;

import com.huang.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@ResponseBody
public class SearchController {
    @Autowired
    private SearchService searchService;

//    @GetMapping("/parse/{keyword}")
//    public Boolean parse(@PathVariable("keyword") String keywords) throws Exception {
//        return contentService.parseContent(keywords);
//    }

    @GetMapping("/searchBlog")
    public List<Map<String, Object>> search(@RequestParam("keyWord") String keyWord,
                                            @RequestParam("pageNo") int pageNo,
                                            @RequestParam("pageSize") int pageSize) throws IOException {

        return searchService.searchPagehighlighter(keyWord, pageNo, pageSize, "blog", "title");

    }
    @GetMapping("/searchVideo")
    public List<Map<String, Object>> searchVideo(@RequestParam("keyWord") String keyWord,
                                                 @RequestParam("pageNo") int pageNo,
                                                 @RequestParam("pageSize") int pageSize) throws IOException {


        return searchService.searchPagehighlighter(keyWord, pageNo, pageSize, "video", "title");

    }
    @GetMapping("/searchDiscuss")
    public List<Map<String, Object>> searchDiscuss(@RequestParam("keyWord") String keyWord,
                                                   @RequestParam("pageNo") int pageNo,
                                                   @RequestParam("pageSize") int pageSize) throws IOException {

        return searchService.searchPagehighlighter(keyWord, pageNo, pageSize, "discuss", "title");

    }
}
