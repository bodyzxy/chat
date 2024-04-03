package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class FileService {

    private final VectorStore vectorStore;
    private final TokenTextSplitter tokenTextSplitter;

    public void fileUpdate(MultipartFile file) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件名类型
        String contentType = file.getContentType();
        //获取文件字节数组
        byte[] bytes = file.getBytes();
        //创建临时文件用来存储临时文件
        Path tempFile = Files.createTempFile("temp-", fileName);
        Files.write(tempFile,bytes);

        Resource resource = new FileSystemResource(tempFile.toFile());
        TextReader textReader = new TextReader(resource);
        textReader.getCustomMetadata().put(resource.getFilename(), resource.getFile());
        vectorStore.accept(tokenTextSplitter.apply(textReader.get()));
    }
}
