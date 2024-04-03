package com.example;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@SpringBootTest
@Slf4j
class ChatApplicationTests {
    @Autowired
    private ResourceLoader resourceLoader;

    private Resource resource;

    // 在需要的地方加载资源
    public void loadResource() {
        resource = resourceLoader.getResource("classpath:data/001.txt");
    }
    @Test
    void test() throws IOException {
        loadResource();
        TextReader textReader = new TextReader(resource);
        textReader.getCustomMetadata().put(resource.getFilename(), resource.getFile());
        System.out.println(textReader.get());

    }
    

}
