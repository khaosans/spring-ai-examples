package habuma.springaiessentialexample.service;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LangChain4JService {

    private final ChatLanguageModel model;
    private final ChatMemory chatMemory;

    public LangChain4JService(@Value("${spring.ai.openai.api-key}") String apiKey,
                              @Value("${spring.ai.openai.chat.options.model}") String modelName) {
        this.model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
        this.chatMemory = TokenWindowChatMemory.builder()
                .maxTokens(2000, new OpenAiTokenizer(apiKey))
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