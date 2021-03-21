package com.huang.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sysnotify {
    private int userId;
    private int sendId;
    private String msg;
    private String pl;
    private String time;
    private String nickName;
    private String imgUrl;
    private int state;
    private int id;
    private String videoImg;
    private String type;
}
