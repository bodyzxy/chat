package com.example.service;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.openai.OpenAiEmbeddingClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 对pdf进行处理
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfService {

    private final JdbcTemplate jdbcTemplate;
    @Value("${spring.ai.openai.api-key}")
    private String defaultApiKey;
    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;
    private final TokenTextSplitter tokenTextSplitter;

    public void savePdf(MultipartFile file) {
        try {
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件名类型
            String contentType = file.getContentType();
            //获取文件字节数组
            byte[] bytes = file.getBytes();
            //创建临时文件用来存储临时文件
            Path tempFile = Files.createTempFile("temp-", fileName);
            Files.write(tempFile,bytes);
            // 创建一个 FileSystemResource 对象
            FileSystemResource fileSystemResource = new FileSystemResource(tempFile.toFile());

            //配置阅读pdf
            PdfDocumentReaderConfig build = PdfDocumentReaderConfig.builder()
                    .withPageExtractedTextFormatter(
                            new ExtractedTextFormatter
                                    .Builder()
                                    .withNumberOfTopPagesToSkipBeforeDelete(1)
                                    .withNumberOfBottomTextLinesToDelete(3)
                                    .build()
                    )
                    .withPagesPerDocument(1)
                    .build();
            PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(fileSystemResource, build);
            VectorStore vectorStore = randomGetVectorStore();
            vectorStore.accept(tokenTextSplitter.apply(pagePdfDocumentReader.get()));
        } catch (IOException e) {
            log.info(e.getMessage());
        }

    }
    public VectorStore randomGetVectorStore(){
        OpenAiApi openAiApi = new OpenAiApi(baseUrl, defaultApiKey);
        EmbeddingClient openAiEmbeddingClient = new OpenAiEmbeddingClient(openAiApi);
        return new PgVectorStore(jdbcTemplate,openAiEmbeddingClient);
    }
}
