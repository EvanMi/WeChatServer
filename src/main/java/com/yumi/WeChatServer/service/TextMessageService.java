package com.yumi.WeChatServer.service;

import com.alibaba.fastjson2.JSON;
import com.yumi.WeChatServer.dao.UrlInfoDao;
import com.yumi.WeChatServer.domain.commands.TextCommandProcessor;
import com.yumi.WeChatServer.domain.commands.UrlInfoSearchCommandProcessor;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import com.yumi.WeChatServer.util.MessageUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.yumi.WeChatServer.domain.commands.CommandConstant.COMMAND_SPLIT;
import static com.yumi.WeChatServer.domain.commands.CommandConstant.WELCOME_TEXT;

@Service
public class TextMessageService {
    private static Logger logger = LoggerFactory.getLogger(TextMessageService.class);
    @Resource
    private UrlInfoDao urlInfoDao;
    @Resource
    private Map<String, TextCommandProcessor> commandMap = new HashMap<>();

    @Resource
    private UrlInfoSearchCommandProcessor urlInfoSearchCommandProcessor;

    private final ConcurrentHashMap<String, Long> limitMap = new ConcurrentHashMap<>();


    public String processText(TextRequest req) {
        logger.info("req: {}", JSON.toJSONString(req));
        String content = req.getContent();
        if (!StringUtils.hasText(content)) {
            TextResp resp = new TextResp();
            resp.setContent("多少说点什么吧~");
            resp.setCreateTime(System.currentTimeMillis() / 1000);
            resp.setFromUserName(req.getToUserName());
            resp.setToUserName(req.getFromUserName());
            resp.setMsgType(req.getMsgType());
            return MessageUtil.textMessageToXml(resp);
        }
        if (content.equals("?help")) {
            return MessageUtil.textMessageToXml(getWelcome(req));
        }
        String[] split = content.split(COMMAND_SPLIT);
        TextCommandProcessor textCommandProcessor = commandMap.get(split[0]);
        if (null != textCommandProcessor) {
            return MessageUtil.textMessageToXml(textCommandProcessor.process(req));
        }
        TextResp process = urlInfoSearchCommandProcessor.process(req);
        if (StringUtils.hasText(process.getContent())) {
            return MessageUtil.textMessageToXml(process);
        }
        //兜底限流
        Long currentTimestamp = limitMap.computeIfAbsent(req.getFromUserName(), key -> System.currentTimeMillis());
        if (System.currentTimeMillis() - currentTimestamp < 7200 * 1000) {
            return "";
        }
        limitMap.put(req.getFromUserName(), System.currentTimeMillis());
        return MessageUtil.textMessageToXml(getWelcome(req));
    }

    private TextResp getWelcome(TextRequest req) {
        TextResp resp = new TextResp();
        resp.setContent(WELCOME_TEXT);
        resp.setCreateTime(System.currentTimeMillis() / 1000);
        resp.setFromUserName(req.getToUserName());
        resp.setToUserName(req.getFromUserName());
        resp.setMsgType(req.getMsgType());
        return resp;
    }
}
