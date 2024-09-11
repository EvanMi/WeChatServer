package com.yumi.WeChatServer.service;

import com.yumi.WeChatServer.domain.message.req.TextRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMessageServiceTests {
    @Resource
	private TextMessageService textMessageService;


    @Test
    public void testAddUrlInfo() {
        TextRequest textRequest = new TextRequest();
        textRequest.setContent("[add]#扫码登录真的很简单-附完整源码、视频讲解#https://mp.weixin.qq.com/s/dxapdiBwQfpcVeMwA9MiLQ#article#大厂文章");
        textRequest.setFromUserName("test");
        textRequest.setToUserName("sys");
        textRequest.setCreateTime(System.currentTimeMillis());
        textRequest.setMsgType("text");
        textRequest.setMsgId(System.currentTimeMillis());
        textMessageService.processText(textRequest);
    }

    @Test
    public void testListUrlInfo() {
        TextRequest textRequest = new TextRequest();
        textRequest.setContent("?#扫码登录并不难");
        textRequest.setFromUserName("test");
        textRequest.setToUserName("sys");
        textRequest.setCreateTime(System.currentTimeMillis());
        textRequest.setMsgType("text");
        textRequest.setMsgId(System.currentTimeMillis());
        textMessageService.processText(textRequest);
    }
}
