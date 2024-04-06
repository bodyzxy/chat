package com.example.entity;

import lombok.Data;

@Data
public class GenerateImagesRequest {
    /**
     * 提示
     */
    private String prompt;
    /**
     * 系数（设置越高，重复越低）
     */
    private float temperature;
    /**
     * 最大生成长度
     */
    private int maxTokens;
    /**
     * 停止字符
     */
    private String stop;
    /**
     * 生成数量
     */
    private int logprobs;
    /**
     * 是否回显
     */
    private boolean echo;
    /**
     * 生成图片数量
     */
    private int num;
}
