package com.huang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private Integer id;
    private String title;
    private String url;
    private Integer point;
    private Integer collection;
    private Integer views;
    private Integer size;
    private String type;
    private String time;
    private Integer upId;
    private String upName;
    private String des;
    private String imgUrl;
    private int jur;
    private int pay;

    public void addView(){
        this.views++;
    }

    public void addPoint(){
        this.point++;
    }

    public void addCollection(){
        this.collection++;
    }
}
