package com.gugu.test;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class HelloWorld {
    public static void main(String[] args) {
        String apiKey = "demo";
        OpenAiChatModel model = OpenAiChatModel.withApiKey(apiKey);
        String answer = model.generate("Say 'Hello World'");
        System.out.println(answer); // Hello World


    }
}
