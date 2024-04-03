package com.example.controller;

import com.example.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 上传文件
     * @param file
     */
    @GetMapping("/update")
    public void fileUpdate(@RequestParam MultipartFile file) throws IOException {
        fileService.fileUpdate(file);
    }
}
