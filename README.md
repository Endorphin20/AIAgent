### 项目描述
- 基于 Spring Boot 3 + Spring AI + DashScope + RAG + Tool Calling + MCP + ReAct 模式构建的垂直情
感问答与通用任务执行一体化 Agent 平台，支持多轮对话、记忆持久化、知识库问答、任务规划与多工具调用。
### 技术亮点
- 分层智能体架构：参考 OpenManus 设计分层智能体架构，抽象 BaseAgent、ReActAgent、ToolCallAgent、
YManus 等核心组件，自主实现基于 ReAct 的 Think-Act 执行链路，而非直接依赖 Spring AI 默认代理
- RAG 能力实践：完成文档加载、Query Rewrite、向量检索与上下文注入链路，支持基于本地知识库的增强问答，
并结合 PGVector 实现知识库向量数据持久化
- 工具调用编排：基于 Spring AI 手动管理 Tool Calling 上下文与执行流程，支持网页搜索、网页抓取、资源下载、
PDF 生成等多工具协同调用，提升了对复杂任务工具决策与执行流程的可控性。
