package habuma.springaiessentialexample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.stereotype.Component; // Import the Component annotation

@Component // Add this annotation to make it a Spring bean
public class PersistentChatMemoryStore implements ChatMemoryStore {
    private final Map<Object, List<ChatMessage>> memoryStore = new HashMap<>();

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        // Retrieve messages for the given memory ID
        return memoryStore.getOrDefault(memoryId, new ArrayList<>());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        // Update messages for the given memory ID
        memoryStore.put(memoryId, messages);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        // Remove messages for the given memory ID
        memoryStore.remove(memoryId);
    }
}