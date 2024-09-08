package com.gugu.test;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
    static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    public static void main(String[] args) {

        String apiKey = "demo";
        OpenAiChatModel model = OpenAiChatModel.withApiKey(apiKey);
        String answer = model.generate("Say 'Hello World'");
//        System.out.println(answer); // Hello World
        logger.info(answer);
    }
}
