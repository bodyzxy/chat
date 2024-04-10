package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiAudioTranscriptionClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ai.openai.api.OpenAiAudioApi;

import java.io.IOException;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
class ChatApplicationTests {
    @Autowired
    private ResourceLoader resourceLoader;

    private OpenAiAudioTranscriptionClient transcriptionClient;

    @Value("${spring.ai.openai.api-key}")
    private String key;

    @Value("classpath:/data/LettingGo.flac")
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

    @Test
    void transcriptionTestWithOptions(){
        OpenAiAudioApi api = new OpenAiAudioApi(key);
        transcriptionClient = new OpenAiAudioTranscriptionClient(api);
        OpenAiAudioApi.TranscriptResponseFormat responseFormat = OpenAiAudioApi.TranscriptResponseFormat.VTT;
        OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .withLanguage("zh-CN")
                .withPrompt("Ask not this, but ask that")
                .withTemperature(0f)
                .withResponseFormat(responseFormat)
                .build();
        AudioTranscriptionPrompt transcriptionPrompt = new AudioTranscriptionPrompt(resource,transcriptionOptions);
        AudioTranscriptionResponse response = transcriptionClient.call(transcriptionPrompt);
        System.out.print(response.getResults());
    }
    

}
