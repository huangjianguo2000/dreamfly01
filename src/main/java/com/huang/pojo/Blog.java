package com.huang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private Integer id;
    private String title;
    private Integer userId;
    private String time;
    private String text;
    private int browseNum;
    private int upNum;
    private int collectionNum;
    private String imgUrl;

}
