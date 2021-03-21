package com.huang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discuss {
    private Integer userId;
    private String title;
    private String des;
    private int views;
    private int answer;
    private int id;
    private String imgUrl;

    public void addView(){
        this.views++;
    }
    public void addaAnswer(){
        this.answer++;
    }

}
