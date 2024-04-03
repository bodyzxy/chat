package com.example.controller;

import com.example.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/pdf")
@Slf4j
@RequiredArgsConstructor
@Controller
public class PdfController {

    private final PdfService pdfService;

    /**
     * 对上传的pdf进行向量化
     * @param file
     */
    @PostMapping("/upload")
    public void upload(@RequestParam MultipartFile file){
        pdfService.savePdf(file);
    }
}
