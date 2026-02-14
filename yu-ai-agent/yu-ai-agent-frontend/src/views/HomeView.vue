<template>
  <div class="home">
    <h1>AI 应用中心</h1>
    <div class="app-list">
      <router-link to="/love-master" class="app-item">
        <h2>AI 恋爱大师</h2>
        <p>智能情感顾问，为你解答情感困惑</p>
      </router-link>
      <router-link to="/super-agent" class="app-item">
        <h2>AI 超级智能体</h2>
        <p>全能AI助手，解决各类问题</p>
      </router-link>
    </div>
  </div>
</template>

<style scoped>
.home {
  padding: 20px;
  text-align: center;
}

.app-list {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 40px;
}

.app-item {
  width: 200px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-decoration: none;
  color: inherit;
  transition: all 0.3s;
}

.app-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

h2 {
  margin-bottom: 10px;
}
</style>