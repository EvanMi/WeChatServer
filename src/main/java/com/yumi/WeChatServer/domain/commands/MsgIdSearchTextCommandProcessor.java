package com.yumi.WeChatServer.domain.commands;

import com.yumi.WeChatServer.dao.MsgIdDao;
import com.yumi.WeChatServer.dao.UrlInfoDao;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static com.yumi.WeChatServer.domain.commands.CommandConstant.COMMAND_SPLIT;

@Component("[msg]")
public class MsgIdSearchTextCommandProcessor extends AdminTextCommandProcessor {
    @Resource
    private MsgIdDao msgIdDao;

    @Override
    protected TextResp processAdmin(TextRequest req) {
            TextResp resp = new TextResp();
            resp.setCreateTime(System.currentTimeMillis() / 1000);
            resp.setFromUserName(req.getToUserName());
            resp.setToUserName(req.getFromUserName());
            resp.setMsgType(req.getMsgType());
        try {
            String content = req.getContent();
            String[] split = content.split(COMMAND_SPLIT);
            String msgIdByTitle = msgIdDao.getMsgIdByTitle(split[1]);
            resp.setContent(msgIdByTitle);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setContent("没有找到");
        }
        return resp;
    }
}
