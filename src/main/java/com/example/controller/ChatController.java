package com.example.controller;

import com.example.entity.AiImage;
import com.example.entity.GenerateImagesRequest;
import com.example.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1/chat")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 通过pdf进行聊天
     * @param message
     * @return
     */
    @GetMapping("/")
    public String chat(@RequestParam String message){
        return chatService.chat(message);
    }

    /**
     * 通过文件内容聊天
     * @param message
     * @return
     */
    @GetMapping("/file")
    public String chatFile(@RequestParam String message){
        return chatService.chat(message);
    }

    /**
     * 生成图片
     * @param generateImagesRequest
     * @return
     */
    @PostMapping("/aiImage")
    public String aiImage(@RequestBody GenerateImagesRequest generateImagesRequest){
        return chatService.aiImage(generateImagesRequest);
    }

    /**
     * 生成图片spring官网
     * https://docs.spring.io/spring-ai/reference/api/clients/image/openai-image.html
     * @param aiImage
     * @return
     */
    @PostMapping("/aiImage2")
    public String aiImage2(@RequestBody AiImage aiImage){
        return chatService.aiImage2(aiImage);
    }
}
