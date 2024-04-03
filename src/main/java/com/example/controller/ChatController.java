package com.example.controller;

import com.example.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
