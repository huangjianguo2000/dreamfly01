package com.huang.utils;

import org.springframework.stereotype.Service;


public class Constant {
    public final String DreamFly_user_update_list = "DreamFly_user_update_list";
    public final String DreamFly_ChatMsg_List = "DreamFly_ChatMsg_List"; //存connection 3-10
    public final String DreamFly_ChatMsg = "DreamFly_ChatMsg";//存聊天记录
    public final String DreamFly_user = "DreamFly_user"; //存用户信息 + phoneNumber
    public final String DreamFly_AllChatMessage = "DreamFly_AllChatMessage"; //当前聊天窗口的全部聊天记录
    public final String DreamFly_NotRead_Message = "DreamFly_NotRead_Message"; //当前聊天窗口的全部未读聊天记录
    public final String DreamFly_AllBlog = "DreamFly_AllBlog"; //存下所有的博客
    public final String DreamFly_AllDiscuss = "DreamFly_AllDiscuss"; //存下所有的讨论
    public final String DreamFly_AllBlog_User = "DreamFly_AllBlog_User"; //存下这个人所有的博客
    public final String DreamFly_AllDiscuss_User = "DreamFly_AllDiscuss_User"; //存下这个人所有的博客
    public final String DreamFly_Discuss = "DreamFly_AllDiscuss_User"; //存下讨论
    public final String DreamFly_Discuss_Update_list = "DreamFly_Discuss_Update_list"; //存下需要更新的讨论列表
    public final String DreamFly_All_Video = "DreamFly_All_Video";
    public final String DreamFly_Video = "DreamFly_Video";
    public final String DreamFly_Video_UserId = "DreamFly_Video_UserId";//用户发布的所有视频
    public final String DreamFly_Answer_Of_Discuss="DreamFly_Answer_Of_Discuss"; //某个讨论的回答
    public final String DreamFly_Video_Update_List = "DreamFly_Video_Update_List";
    public final String DreamFly_Comment_VideoId = "DreamFly_Comment_VideoId";
    public final String DreamFly_All_Notify_Of_User = "DreamFly_All_Notify_Of_User";
    public final String DreamFly_All_Question_PageSize = "DreamFly_All_Question_PageSize";
    public final String DreamFly_Question_id = "DreamFly_Question_id";
    public final String DreamFly_BuyVideo_userId = "DreamFly_BuyVideo_userId";
    public final String DreamFly_Collection_Video_userId = "DreamFly_Collection_Video_userId";
    public final String DreamFly_Video_tuijian = "DreamFly_Video_tuijian";



    public String getDreamFly_AllBlog_User(int userId){
        return this.DreamFly_AllBlog_User + userId;
    }
    public String getDreamFly_AllDiscuss_User(int userId){
        return this.DreamFly_AllDiscuss_User + userId;
    }
    public String getDreamFly_Video(int videoId){
        return this.DreamFly_Video + videoId;
    }
    public String getDreamFly_Answer_Of_Discuss(int discussId){
        return this.DreamFly_Answer_Of_Discuss + discussId;
    }
    public String getDreamFly_Video_UserId(int userId){
        return this.DreamFly_Video_UserId + userId;
    }
    public String getDreamFly_user(int num){
        return this.DreamFly_user + num;
    }
    public String getDreamFly_Comment_VideoId(int videoId){
        return this.DreamFly_Comment_VideoId + videoId;
    }
    public String getDreamFly_All_Notify_Of_User(int userId){
        return this.DreamFly_All_Notify_Of_User + userId;
    }
    public String getDreamFly_Question_id(int id){
        return this.DreamFly_Question_id + id;
    }
    public String getDreamFly_BuyVideo_userId(int userId){
        return this.DreamFly_BuyVideo_userId + userId;
    }
    public String getDreamFly_Collection_Video_userId(int userId){
        return this.DreamFly_Collection_Video_userId + userId;
    }
}
