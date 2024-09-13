package habuma.springaiessentialexample.service;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LangChain4JService {

    private final ChatLanguageModel model;
    private final ChatMemory chatMemory;

    public LangChain4JService(@Value("${ollama.base-url}") String baseUrl,
                              @Value("${ollama.model-name}") String modelName) {
        this.model = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(60))
                .build();
        this.chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
    }

    public String generateJoke(String subject) {
        JokeService jokeService = AiServices.builder(JokeService.class)
                .chatLanguageModel(model)
                .chatMemory(chatMemory)
                .build();
        return jokeService.tellJoke(subject);
    }

    public String chatCompletion(String input) {
        ChatService chatService = AiServices.builder(ChatService.class)
                .chatLanguageModel(model)
                .chatMemory(chatMemory)
                .build();
        return chatService.chat(input);
    }

    private interface JokeService {
        String tellJoke(String subject);
    }

    private interface ChatService {
        String chat(String message);
    }
}