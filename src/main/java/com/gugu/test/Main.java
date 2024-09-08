package com.gugu.test;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
// 原文 https://mp.weixin.qq.com/s/x6hVvxwp4yAZkrgwavB1Gw
public class Main {
    static final Logger log = LoggerFactory.getLogger(HelloWorld.class);
    static final String API_KEY = "demo";

    public static void main(String[] args) {

//        知识采集
        URL docUrl = Main.class.getClassLoader().getResource("笑话.txt");
        if(docUrl==null){
            log.error("未获取到文件");
        }
        Document document = getDocument(docUrl);
        if(document==null){
            log.error("加载文件失败");
        }
//        文档切分
//        分段大小（一个分段中最大包含多少个token）、重叠度（段与段之前重叠的token数）、分词器（将一段文本进行分词，得到token）
        DocumentSplitter splitter = DocumentSplitters.recursive(150,10,new OpenAiTokenizer());
        splitter.split(document);

        OpenAiEmbeddingModel embeddingModel = new OpenAiEmbeddingModel.OpenAiEmbeddingModelBuilder().apiKey(API_KEY)
//                .baseUrl(BASE_URL)
                .build();
        log.info("当前的模型是: {}", embeddingModel.modelName());
        String text = "两只眼睛";
        Embedding embedding = embeddingModel.embed(text).content();
        log.info("文本:{}的嵌入结果是:\n{}", text, embedding.vectorAsList());
        log.info("它是{}维的向量", embedding.dimension());

//        Client client = new Client(CHROMA_URL);
//        EmbeddingFunction embeddingFunction = new OpenAIEmbeddingFunction(API_KEY, OPEN_AI_MODULE_NAME);
//        client.createCollection(CHROMA_DB_DEFAULT_COLLECTION_NAME,null,true, embeddingFunction);
//
//        EmbeddingStore<TextSegment> embeddingStore = ChromaEmbeddingStore.builder().baseUrl(CHROMA_URL).collectionName(CHROMA_DB_DEFAULT_COLLECTION_NAME).build();
//        segments.forEach(segment->{
//            Embedding embedding = embeddingModel.embed(segment).content();
//            embeddingStore.add(embedding, segment);
//        });
//
//        Embedding queryEmbedding = embeddingModel.embed(qryText).content();
//
//        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder().queryEmbedding(queryEmbedding).maxResults(1).build();
//        EmbeddingSearchResult<TextSegment> embeddedEmbeddingSearchResult = embeddingStore.search(embeddingSearchRequest);
//        List<EmbeddingMatch<TextSegment>> embeddingMatcheList = embeddedEmbeddingSearchResult.matches();
//        EmbeddingMatch<TextSegment> embeddingMatch = embeddingMatcheList.get(0);
//        TextSegment TextSegment = embeddingMatch.embedded();
//
//
//        PromptTemplate promptTemplate = PromptTemplate.from("基于如下信息进行回答:\n" +
//                "{{context}}\n" +
//                "提问:\n" +
//                "{{question}}");
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("context", textSegment.text());
//        variables.put("question", QUESTION);
//        prompt = promptTemplate.apply(variables);
//
//        OpenAiChatModel openAiChatModel =  OpenAiChatModel.builder().apiKey(API_KEY).baseUrl(BASE_URL).modelName(OPEN_AI_MODULE_NAME).temperature(TEMPERATURE_NO_RANDOM).build();
//        UserMessage userMessage = prompt.toUserMessage();
//        Response<AiMessage> aiMessageResponse = openAiChatModel.generate(userMessage);
//        String response = aiMessageResponse.content();
    }

    private static Document getDocument(URL resource) {
        Document document = null;
        try{
            Path path = Paths.get(resource.toURI());
            document = FileSystemDocumentLoader.loadDocument(path);
        }catch (URISyntaxException e){
            log.error("加载文件发生异常", e);
        }
        return document;
    }

}
