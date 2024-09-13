# Spring AI Essential Example

This project demonstrates the use of Spring AI with Ollama's Llama2 model to generate jokes and provide chat completions using LangChain4j.

## Prerequisites

- Java 17 or higher
- Maven
- Ollama running locally with the Llama2 model

## Configuration

To use this application, you need to have Ollama running locally with the Llama2 model. Follow these steps:

1. Install Ollama from https://ollama.ai/

2. Pull the Llama2 model:
   ```bash
   ollama pull llama2
   ```

3. Ensure your `src/main/resources/application.properties` file contains the following:

   ```properties
   # Ollama configuration
   ollama.base-url=http://localhost:11434
   ollama.model-name=llama2

   # Configure logging
   logging.level.org.springframework.ai=DEBUG
   logging.level.dev.langchain4j=DEBUG
   logging.level.habuma.springaiessentialexample=DEBUG
   ```

## Running the Application

To run the application:
