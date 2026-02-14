//package com.yupi.yuaiagent.demo.invoke;
//
//
//import jakarta.annotation.Resource;
//import org.springframework.ai.chat.messages.AssistantMessage;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
///**
// * Spring AI 框架调用 AI 大模型（Ollama）
// */
//@Component
//public class OllamaAiInvoke implements CommandLineRunner {
//
//    @Resource
//    private ChatModel ollamaChatModel;
//
//    @Override
//    public void run(String... args) throws Exception {
//        AssistantMessage assistantMessage = ollamaChatModel.call(new Prompt("你好，我是66"))
//                .getResult()
//                .getOutput();
//        System.out.println(assistantMessage.getText());
//    }
//}
// 这里先把ollama注释掉了，需要的话再把yml中的配置开开，把代码还原，把pom中的依赖打开