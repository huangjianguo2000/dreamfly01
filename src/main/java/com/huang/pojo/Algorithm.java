package com.huang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Algorithm {
    private Integer id;
    private String name;
    private String des;
    private String input;
    private String output;
    private String sampleInput;
    private String sampleOutput;
    private String url;
    private Integer rate;
    private String dif;
    private Integer timeLimit;
    private Integer memoryLimit;
}
