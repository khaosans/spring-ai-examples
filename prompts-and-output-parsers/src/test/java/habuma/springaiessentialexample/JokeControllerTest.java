package habuma.springaiessentialexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.mockito.Mockito;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.Generation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;

@WebMvcTest(JokeController.class)
class JokeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatClient chatClient;

    @MockBean
    private PromptTemplate promptTemplate;

    @Test
    void testTellJoke() throws Exception {
        String subject = "knock-knock";
        String expectedJoke = "This is a joke about knock-knock";

        Prompt mockPrompt = new Prompt("Tell me a joke about " + subject);
        when(promptTemplate.create(Map.of("subject", subject))).thenReturn(mockPrompt);

        ChatResponse mockResponse = Mockito.mock(ChatResponse.class);
        Generation mockGeneration = Mockito.mock(Generation.class);
        when(mockGeneration.getOutput()).thenReturn(new AssistantMessage(expectedJoke));
        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);
        when(mockResponse.getResult()).thenReturn(mockGeneration);

        mockMvc.perform(get("/joke?subject=" + subject))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"subject\":\"" + subject + "\",\"joke\":\"" + expectedJoke + "\"}"));
    }

    @Test
    void testChatCompletion() throws Exception {
        String input = "cats";
        String expectedResponse = "Why did the cat sit on the computer? Because it wanted to keep an eye on the mouse!";

        ChatResponse mockResponse = Mockito.mock(ChatResponse.class);
        Generation mockGeneration = Mockito.mock(Generation.class);
        when(mockGeneration.getOutput()).thenReturn(new AssistantMessage(expectedResponse));
        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);
        when(mockResponse.getResult()).thenReturn(mockGeneration);

        mockMvc.perform(get("/chat-completion?input=" + input))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}