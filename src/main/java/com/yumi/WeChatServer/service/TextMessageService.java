package com.yumi.WeChatServer.service;

import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import com.yumi.WeChatServer.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TextMessageService {
    private static Logger logger = LoggerFactory.getLogger(TextMessageService.class);

    public String processText(TextRequest req) {
        logger.info("req: {}", req);
        TextResp resp = new TextResp();
        resp.setContent("正在装修中");
        resp.setCreateTime(System.currentTimeMillis() / 1000);
        resp.setFromUserName(req.getToUserName());
        resp.setToUserName(req.getFromUserName());
        resp.setMsgType(req.getMsgType());
        return MessageUtil.textMessageToXml(resp);
    }
}
