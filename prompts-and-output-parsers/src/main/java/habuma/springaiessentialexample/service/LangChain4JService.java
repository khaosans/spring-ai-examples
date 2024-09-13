package habuma.springaiessentialexample.service;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Service
public class LangChain4JService {

    private final OllamaChatModel model;
    private final ChatMemory chatMemory;

    @Autowired
    public LangChain4JService(PersistentChatMemoryStore persistentChatMemoryStore) {
        this.model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama2")
                .timeout(Duration.ofSeconds(60))
                .build();

        this.chatMemory = MessageWindowChatMemory.builder()
                .id("12345")
                .maxMessages(10)
                .chatMemoryStore(persistentChatMemoryStore)
                .build();
        
        if (this.model == null || this.chatMemory == null) {
            throw new IllegalStateException("Chat model or memory is not initialized.");
        }
    }

    public CompletableFuture<String> generateJoke(@UserMessage String message) { 
        JokeService jokeService = AiServices.builder(JokeService.class)
                .chatLanguageModel(model)
                .chatMemory(chatMemory)
                .build();

        return CompletableFuture.supplyAsync(() -> jokeService.tellJoke(message));
    }

    public CompletableFuture<String> chatCompletion(@UserMessage String input) {
        ChatService chatService = AiServices.builder(ChatService.class)
                .chatLanguageModel(model)
                .chatMemory(chatMemory)
                .build();

        return CompletableFuture.supplyAsync(() -> chatService.chat(input));
    }

    private interface JokeService {
        String tellJoke(@UserMessage String subject);
    }

    private interface ChatService {
        String chat(@UserMessage String message);
    }
}