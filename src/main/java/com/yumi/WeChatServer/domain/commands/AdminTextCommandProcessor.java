package com.yumi.WeChatServer.domain.commands;

import ch.qos.logback.core.util.StringUtil;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

public abstract class AdminTextCommandProcessor implements TextCommandProcessor {
    @Value("${com.yumi.wei.xin.admin.id}")
    private String adminId;

    @Override
    public TextResp process(TextRequest req) {
        String fromUserName = req.getFromUserName();
        if (!StringUtils.hasText(fromUserName) || !adminId.equals(fromUserName)) {
            TextResp resp = new TextResp();
            resp.setContent("你是谁呀?");
            resp.setCreateTime(System.currentTimeMillis() / 1000);
            resp.setFromUserName(req.getToUserName());
            resp.setToUserName(req.getFromUserName());
            resp.setMsgType(req.getMsgType());
            return resp;
        }
        return processAdmin(req);
    }

    protected abstract TextResp processAdmin(TextRequest req);
}
