package com.yumi.WeChatServer.service;

import com.yumi.WeChatServer.domain.message.req.EventRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import com.yumi.WeChatServer.util.MessageUtil;
import org.springframework.stereotype.Service;

import static com.yumi.WeChatServer.domain.commands.CommandConstant.WELCOME_TEXT;

@Service
public class SubscribeEventService {
    public String processEvent(EventRequest req) {
        TextResp resp = new TextResp();
        resp.setCreateTime(System.currentTimeMillis() / 1000);
        resp.setFromUserName(req.getToUserName());
        resp.setToUserName(req.getFromUserName());
        resp.setMsgType(req.getMsgType());
        resp.setContent(WELCOME_TEXT);
        return MessageUtil.textMessageToXml(resp);
    }
}
