package com.yumi.WeChatServer.service;

import com.yumi.WeChatServer.domain.message.req.TextRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMessageServiceTest {
    @Resource
	private TextMessageService textMessageService;


    @Test
    public void testAddUrlInfo() {
        TextRequest textRequest = new TextRequest();
        textRequest.setContent("[add]#吃西瓜的人#http://www.baidu.com#article#default");
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
        textRequest.setContent("?#西瓜真是一个好东西的 是多少是第三代");
        textRequest.setFromUserName("test");
        textRequest.setToUserName("sys");
        textRequest.setCreateTime(System.currentTimeMillis());
        textRequest.setMsgType("text");
        textRequest.setMsgId(System.currentTimeMillis());
        textMessageService.processText(textRequest);
    }
}
