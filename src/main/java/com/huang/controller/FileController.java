package com.huang.controller;


import com.alibaba.fastjson.JSON;
import com.huang.dao.BlogDao;
import com.huang.dao.DiscussDao;
import com.huang.dao.UserDao;
import com.huang.dao.VideoDao;
import com.huang.pojo.Video;
import com.huang.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 */
@CrossOrigin
@Controller
@ResponseBody
public class FileController {

    @Autowired
    UserDao userDao;
    @Autowired
    VideoDao videoDao;
    @Autowired
    DiscussDao discussDao;
    @Autowired
    BlogDao blogDao;
    @Autowired
    SearchService searchService;

    public static void deleteFile(File file) {
        if (file.exists()) {//判断路径是否存在
            if (file.isFile()) {//boolean isFile():测试此抽象路径名表示的文件是否是一个标准文件。
                file.delete();
            } else {//不是文件，对于文件夹的操作
                //保存 路径D:/1/新建文件夹2  下的所有的文件和文件夹到listFiles数组中
                File[] listFiles = file.listFiles();//listFiles方法：返回file路径下所有文件和文件夹的绝对路径
                for (File file2 : listFiles) {
                    /*
                     * 递归作用：由外到内先一层一层删除里面的文件 再从最内层 反过来删除文件夹
                     *    注意：此时的文件夹在上一步的操作之后，里面的文件内容已全部删除
                     *         所以每一层的文件夹都是空的  ==》最后就可以直接删除了
                     */
                    deleteFile(file2);
                }
            }
            file.delete();
        } else {
            System.out.println("该file路径不存在！！");
        }
    }

    @RequestMapping("/personal/uploadCard")
    public String fileLoad(
                           @RequestParam("card_num") String card_num,
                           @RequestParam("positive") MultipartFile positive,
                           @RequestParam("back") MultipartFile back,
                          @RequestParam("user_id") int user_id){
        return "OK";

    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile upload, @RequestParam("id") int id) {

        String url = userDao.selectOneById(id).getImgUrl();
        String p = url.substring(47);
        //http://www.handsomehuang.cn:8081/img2/userHead/80055ef5eb0ae91cca60de6f23aad7e2.jpg
        String p1 = "/home/tomcat/ROOT/img2/userHead/" + p;
        System.out.println(p1);
        File f = new File(p1);
        if (!p.equals("80055ef5eb0ae91cca60de6f23aad7e2.jpg"))
            deleteFile(f);
        p1 = "";
        if (upload.isEmpty()) {
            System.out.println("文件为空空");
        }
        String path = "/home/tomcat/ROOT/img2/userHead/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        //获取上传文件的后缀名
        String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().indexOf("."));
        // 获取上传文件的名称
        //String filename = upload.getOriginalFilename();
        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String filename = uuid;
        System.out.println(filename);
        p1 = "http://www.handsomehuang.cn:8081/img2/userHead/" + filename;
        try {
            upload.transferTo(new File(path, filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDao.updateImgUrl(p1, id);
        discussDao.updateImgUrl(p1, id);
        blogDao.updateImgUrl(p1, id);
        return p1;
    }
    @RequestMapping("/enterprise/applyProject")
    public String TfileUpload(        @RequestParam("project_files") MultipartFile upload,
                                      @RequestParam("project_name") String project_name,
                                      @RequestParam("enterprise_id") int enterprise_id,
                                      @RequestParam("project_money") String project_money,
                                      @RequestParam("project_duration") String project_duration,
                                      @RequestParam("project_director") String project_director,
                                      @RequestParam("project_phone") String project_phone,
                                      @RequestParam("project_desc") String project_desc
            ) {


        if (upload.isEmpty()) {
            System.out.println("文件为空空");
        }
        String path = "C://Users//Administrator//Desktop//test";
        File file = new File(path);
            //获取上传文件的后缀名
            String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().indexOf("."));
            // 获取上传文件的名称
            //String filename = upload.getOriginalFilename();
            // 把文件的名称设置唯一值，uuid
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String filename = uuid;
            System.out.println(filename);
            try {
                upload.transferTo(new File(path, filename + ".jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        return "OK";
    }

    //上传视频
    @RequestMapping("/fileUploadVideo")
    public String fileUploadVideo(@RequestParam("file") MultipartFile upload,
                                  @RequestParam("userName") String userName,
                                  @RequestParam("size") int size,
                                  @RequestParam("type") String type,
                                  @RequestParam("time") String time,
                                  @RequestParam("des") String des,
                                  @RequestParam("name") String name,
                                  @RequestParam("imgUrl") String imgUrl,
                                  @RequestParam("id") int id,
                                  @RequestParam("jur") int jur,
                                  @RequestParam("pay") int pay) throws IOException {


        //  System.out.println("接收到了================");
        if (upload.isEmpty()) {
            System.out.println("文件为空空");
        }
        String path = "/home/tomcat/ROOT/video/";
        //  String path = "C:/Users/30611/Desktop/upload";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        //获取上传文件的后缀名
        String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().indexOf("."));
        // 获取上传文件的名称
        //String filename = upload.getOriginalFilename();
        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String filename = uuid;
        String url = "http://www.handsomehuang.cn:8081/video/" + filename;
        try {
            upload.transferTo(new File(path, filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Video video = new Video();
        video.setUpName(userName);
        video.setSize(size);
        video.setType(type);
        video.setTime(time);
        video.setDes(des);
        video.setTime(name);
        video.setImgUrl(imgUrl);
        video.setUpId(id);
        video.setUrl(url);
        video.setJur(jur);
        video.setPay(pay);
        videoDao.insertVideo(video);
        searchService.insertVideo(video);
        return "上传成功";
    }

    //上传视频的图片
    @RequestMapping("/justUplodImg")
    public String fileUploadVideo(@RequestParam("file") MultipartFile upload) {


        //  System.out.println("接收到了");
        if (upload.isEmpty()) {
            System.out.println("文件为空空");
        }
        ///home/tomcat/ROOT/img/userImg
        String path = "/home/tomcat/ROOT/img2/videoImg";
        // String path = "C:/Users/30611/Desktop/upload";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        //获取上传文件的后缀名
        String fileType = upload.getOriginalFilename().substring(upload.getOriginalFilename().indexOf("."));
        // 获取上传文件的名称
        //String filename = upload.getOriginalFilename();
        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String filename = uuid;
        String url = "http://www.handsomehuang.cn:8081/img2/videoImg/" + filename;
        try {
            upload.transferTo(new File(path, filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }
    //实现Spring Boot 的文件下载功能，映射网址为/download
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException {

        // 获取指定目录下的第一个文件
        File scFileDir = new File("C://Users//30611//Desktop//upload");
        File TrxFiles[] = scFileDir.listFiles();
        System.out.println(TrxFiles[0]);
       // String fileName = TrxFiles[0].getName(); //下载的文件名
        String fileName = "nazimi.mp4"; //下载的文件名

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            String realPath = "C://Users//30611//Desktop//upload";
            File file = new File(realPath, fileName);

            // 如果文件名存在，则进行下载
            if (file.exists()) {

                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("Download the song successfully!");
                }
                catch (Exception e) {
                    System.out.println("Download the song failed!");
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

}