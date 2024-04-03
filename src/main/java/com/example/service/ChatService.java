package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    // 系统提示词
    private final static String SYSTEM_PROMPT = """
            你需要使用文档内容对用户提出的问题进行回复，同时你需要表现得天生就知道这些内容，
            不能在回复中体现出你是根据给出的文档内容进行回复的，这点非常重要。
            
            当用户提出的问题无法根据文档内容进行回复或者你也不清楚时，回复不知道即可。
                    
            文档内容如下:
            {documents}
            
            """;

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    /**
     * 通过向量数据库进行检索
     * @param message
     * @return
     */
    public String chatByPdf(String message) {
        //根据文本内容进行相似性检索
        List<Document> documents = vectorStore.similaritySearch(message);
        //将Document列表中每个conntent内容进行拼接
        String collect = documents.stream().map(Document::getContent).collect(Collectors.joining());
        //使用Spring AI提供的模块方式构造SystemMessage对象
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage(Map.of("documents", collect));
        //构建userMessage对象
        UserMessage userMessage = new UserMessage(message);
        //将message对象一并推给CharGPT
        ChatResponse call = chatClient.call(new Prompt(List.of(systemMessage,userMessage)));
        return call.getResult().getOutput().getContent();
    }
}
