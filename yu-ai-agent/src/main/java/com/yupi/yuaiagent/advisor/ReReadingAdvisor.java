package com.yupi.yuaiagent.advisor;

import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;


/**
 * 自定义 Re2 Advisor
 * 可提高大语言模型的推理能力
 */
public class ReReadingAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

	/**
	 * 执行请求前，改写prompt
	 * @param advisedRequest
	 * @return
	 */
	private AdvisedRequest before(AdvisedRequest advisedRequest) {

		Map<String, Object> advisedUserParams = new HashMap<>(advisedRequest.userParams());
		advisedUserParams.put("re2_input_query", advisedRequest.userText());

//		// 更新上下文（多个拦截器之间怎么传递变量，springAI的一个高级特性）
//		advisedRequest = advisedRequest.updateContext(contest -> {
//			contest.put("userMessage", "xxxxxxx");
//			return contest;
//		});

		// userTest中，再让 AI 读一遍原请求
		return AdvisedRequest.from(advisedRequest)
				.userText("""
			    {re2_input_query}
			    Read the question again: {re2_input_query}
			    """)
				.userParams(advisedUserParams)
				.build();
	}

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
		return chain.nextAroundCall(this.before(advisedRequest));
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
		return chain.nextAroundStream(this.before(advisedRequest));
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}