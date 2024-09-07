package com.yumi.WeChatServer.service;

import com.yumi.WeChatServer.domain.message.req.TextMessage;
import com.yumi.WeChatServer.util.MessageUtil;
import org.springframework.stereotype.Service;

@Service
public class TextMessageService {

    public String processText(TextMessage req) {
        com.yumi.WeChatServer.domain.message.resp.TextMessage
                resp = new com.yumi.WeChatServer.domain.message.resp.TextMessage();
        resp.setContent("正在装修中");
        resp.setCreateTime(System.currentTimeMillis() / 1000);
        resp.setFromUserName(req.getToUserName());
        resp.setToUserName(req.getFromUserName());
        resp.setMsgType(req.getMsgType());
        return MessageUtil.textMessageToXml(resp);
    }
}
