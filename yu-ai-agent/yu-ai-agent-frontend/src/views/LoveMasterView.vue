<template>
  <div class="chat-container">
    <div class="chat-messages" ref="messageContainer">
      <div v-for="(msg, index) in messages" :key="index" 
           :class="['message', msg.type === 'user' ? 'user-message' : 'ai-message']">
        <div class="message-content">
          {{ msg.content }}
        </div>
      </div>
    </div>
    <div class="input-container">
      <input v-model="userInput" 
             @keyup.enter="sendMessage" 
             placeholder="输入消息..." 
             :disabled="loading">
      <button @click="sendMessage" :disabled="loading || !userInput.trim()">
        发送
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const messages = ref([])
const userInput = ref('')
const loading = ref(false)
const messageContainer = ref(null)
const chatId = ref(Date.now().toString()) // 生成聊天室ID

const scrollToBottom = () => {
  if (messageContainer.value) {
    setTimeout(() => {
      messageContainer.value.scrollTop = messageContainer.value.scrollHeight
    }, 100)
  }
}

const sendMessage = async () => {
  if (!userInput.value.trim() || loading.value) return
  
  const message = userInput.value
  messages.value.push({ type: 'user', content: message })
  userInput.value = ''
  loading.value = true
  scrollToBottom()

  try {
    const eventSource = new EventSource(
      `http://localhost:8123/api/ai/love_app/chat/sse?message=${encodeURIComponent(message)}&chatId=${chatId.value}`
    )

    eventSource.onmessage = (event) => {
      const response = event.data
      if (response === '[DONE]') {
        eventSource.close()
        loading.value = false
        return
      }
      
      if (messages.value[messages.value.length - 1]?.type === 'ai') {
        messages.value[messages.value.length - 1].content += response
      } else {
        messages.value.push({ type: 'ai', content: response })
      }
      scrollToBottom()
    }

    eventSource.onerror = () => {
      eventSource.close()
      loading.value = false
    }
  } catch (error) {
    console.error('Error:', error)
    loading.value = false
  }
}

onMounted(() => {
  messages.value.push({
    type: 'ai',
    content: '你好！我是AI恋爱大师，很高兴能为你解答情感困惑。请告诉我你的问题吧！'
  })
})
</script>

<style scoped>
.chat-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 20px;
  padding: 10px;
}

.message {
  margin: 10px 0;
  display: flex;
}

.user-message {
  justify-content: flex-end;
}

.message-content {
  max-width: 70%;
  padding: 10px 15px;
  border-radius: 15px;
  word-wrap: break-word;
}

.user-message .message-content {
  background-color: #007AFF;
  color: white;
}

.ai-message .message-content {
  background-color: #E9ECEF;
  color: black;
}

.input-container {
  display: flex;
  gap: 10px;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
}

button {
  padding: 10px 20px;
  background-color: #007AFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>