package com.yumi.WeChatServer.domain.commands;

import com.yumi.WeChatServer.dao.TableManageDao;
import com.yumi.WeChatServer.dao.UrlInfoDao;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import com.yumi.WeChatServer.domain.po.UrlInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.yumi.WeChatServer.domain.commands.CommandConstant.COMMAND_SPLIT;

@Component("[init]")
public class DataBaseInitTextCommandProcessor extends AdminTextCommandProcessor {
    @Resource
    private TableManageDao tableManageDao;

    @Override
    protected TextResp processAdmin(TextRequest req) {
            TextResp resp = new TextResp();
            resp.setCreateTime(System.currentTimeMillis() / 1000);
            resp.setFromUserName(req.getToUserName());
            resp.setToUserName(req.getFromUserName());
            resp.setMsgType(req.getMsgType());
        try {
            tableManageDao.initDb();
            resp.setContent("初始化成功");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setContent("初始化失败");
        }
        return resp;
    }
}
