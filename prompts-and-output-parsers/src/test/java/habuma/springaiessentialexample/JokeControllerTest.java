package habuma.springaiessentialexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.mockito.Mockito;
import org.springframework.ai.chat.Generation; // Add this import statement

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(JokeController.class)
class JokeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatClient chatClient;

    @Test
    void testTellJoke() throws Exception {
        mockMvc.perform(get("/joke?subject=knock-knock"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"subject\":\"knock-knock\",\"joke\":\"This is a joke about knock-knock\"}"));
    }

    @Test
    void testChatCompletion() throws Exception {
        String input = "cats";
        String expectedResponse = "Why did the cat sit on the computer? Because it wanted to keep an eye on the mouse!";

        // Change the mockResponse type to match the expected return type
        ChatResponse mockResponse = Mockito.mock(ChatResponse.class);

        // Ensure the mockResponse returns a ChatResponse object
        when(chatClient.call(any(Prompt.class))).thenReturn(mockResponse);
        when(mockResponse.getResult()).thenReturn(new Generation(expectedResponse)); // Adjusted to return a Generation object

        mockMvc.perform(get("/chat-completion?input=" + input))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}