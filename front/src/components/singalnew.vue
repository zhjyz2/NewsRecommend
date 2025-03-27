<template>
  <div class="bg-yellow-50 p-3 rounded border-3 border-green-500">
    <diV class="font-bold text-3xl">
      {{ news.newTitle }}
    </diV>
    <div class="flex items-center justify-center my-5 text-gray-300 space-x-2">
      <span class="h-[1px] w-screen bg-gray-500"></span>
    </div>
    <div class="flex pt-3 pb-2">
      <div class="pr-3 font-bold"> {{ news.newDate }}</div>
      <div class="pl-3 font-bold">{{ news.newCategory }}</div>
    </div>
    <div class="flex items-center justify-center my-5 text-gray-300 space-x-2">
      <span class="h-[1px] w-screen bg-gray-500"></span>
    </div>
    <div>
      <v-md-preview :text="displayedText"></v-md-preview>
    </div>
    <div class="flex items-center justify-center my-5 text-gray-300 space-x-2">
      <span class="h-[1px] w-screen bg-gray-500"></span>
    </div>
    <div>
      <v-md-preview :text="news.newCnt"></v-md-preview>
    </div>
  </div>
</template>
<script>
import {querybynid} from '~/api/news';
import {ref} from 'vue';

const API_KEY = 'sk-da4352406df54426902bc4af879d1224'; // 替换为你的API密钥


export default {
  name: 'News',
  setup() {
    const onSubmit = (e) => {
      router.push('/newsdetail/' + e.currentTarget.id)
    }
    return {
      onSubmit
    }
  },
  data() {
    return {
      news: [],
      displayedText: ''
    }
  },
  async mounted() {
    const urldate = window.location.href;
    const res = await querybynid(urldate.split("/")[4]);
    this.news = res.data;

    // 固定请求参数
    const requestPayload = {
      model: 'deepseek-chat',
      messages: [
        {
          role: "system",
          content: "你是一个高效的助理，现在需要帮助一个人总结新闻的主要内容为100-200字，在接下来的对话中我会将新闻文本提供给你，请确保在之后的回答中只包含总结后的内容"
        },
        {
          role: "user",
          content: this.news.newCnt // 固定prompt
        }
      ],
      stream: true
    };

    const response = await fetch('https://api.deepseek.com/chat/completions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${API_KEY}`
      },
      body: JSON.stringify(requestPayload)
    });

    const reader = response.body.getReader();
    const decoder = new TextDecoder();

    while (true) {
      const {done, value} = await reader.read();
      if (done) break;

      // 流式数据处理
      const chunk = decoder.decode(value);
      chunk.split('\n')
          .filter(line => line.trim() && line !== 'data: [DONE]')
          .forEach(line => {
            try {
              const data = JSON.parse(line.replace('data: ', ''));
              const content = data.choices[0]?.delta?.content || '';
              // 直接追加内容（如需打字机效果保留逐字添加）
              this.displayedText += content;
            } catch (e) {
              console.warn('解析数据异常:', e);
            }
          });
    }
  }
}


</script>
    
    