package habuma.springaiessentialexample;

public class JokeResponse {
    private String joke;

    // Constructor to initialize the joke
    public JokeResponse(String joke) {
        this.joke = joke;
    }

    // Renamed record to avoid conflict with the class name
    public record JokeDetail(String subject, String jokeContent) {
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}
