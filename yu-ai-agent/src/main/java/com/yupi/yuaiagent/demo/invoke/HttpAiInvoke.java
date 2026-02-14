package com.yupi.yuaiagent.demo.invoke;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

import java.util.*;

/**
 * HTTP 方式调用 AI
 */
public class HttpAiInvoke {

    // 将你的 API Key 放在这里，或者从环境变量、配置文件读取
    private static final String DASHSCOPE_API_KEY = TestApiKey.API_KEY;
    // 如果不使用环境变量，可以硬编码（不推荐生产环境）
    // private static final String DASHSCOPE_API_KEY = "your-api-key-here";

    public static void main(String[] args) {
        if (DASHSCOPE_API_KEY == null || DASHSCOPE_API_KEY.isEmpty()) {
            System.err.println("Error: DASHSCOPE_API_KEY environment variable is not set.");
            return;
        }

        // 使用 Map 和 List 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "qwen-plus");

        Map<String, Object> inputObj = new HashMap<>();
        List<Map<String, String>> messagesList = new ArrayList<>();

        Map<String, String> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", "You are a helpful assistant.");
        messagesList.add(systemMsg);

        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", "你是谁？");
        messagesList.add(userMsg);

        inputObj.put("messages", messagesList);
        requestBody.put("input", inputObj);

        Map<String, String> parametersObj = new HashMap<>();
        parametersObj.put("result_format", "message");
        requestBody.put("parameters", parametersObj);

        try {
            // 使用 Hutool 发起 POST 请求
            HttpResponse response = HttpRequest.post("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation")
                    .header("Authorization", "Bearer " + DASHSCOPE_API_KEY)
                    .header("Content-Type", ContentType.JSON.toString())
                    .body(JSONUtil.toJsonStr(requestBody)) // 将 Map 转换为 JSON 字符串作为请求体
                    .timeout(30000) // 设置超时时间，单位毫秒
                    .execute(); // 执行请求

            // 获取响应状态码和内容
            int statusCode = response.getStatus();
            String responseBody = response.body();

            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

        } catch (Exception e) {
            System.err.println("An error occurred while making the request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}




