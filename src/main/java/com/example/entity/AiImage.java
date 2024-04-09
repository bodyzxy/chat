package com.example.entity;

import lombok.Data;

@Data
public class AiImage {
    //图片描述
    private String prompt;

    //模型AI绘图模型 dall-e-3或dall-e-2（默认)
    private String model;

    //高度
    private Integer height;

    //宽度
    private Integer width;

    //响应格式
    private String format;
}
