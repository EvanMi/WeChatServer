package com.yumi.WeChatServer.domain.commands;

import com.yumi.WeChatServer.dao.UrlInfoDao;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import com.yumi.WeChatServer.domain.po.UrlInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.yumi.WeChatServer.domain.commands.CommandConstant.COMMAND_SPLIT;

@Component("[add]")
public class UrlInfoAddTextCommandProcessor extends AdminTextCommandProcessor {
    @Resource
    private UrlInfoDao urlInfoDao;

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
            UrlInfo urlInfo = new UrlInfo();
            urlInfo.setTitle(split[1]);
            urlInfo.setUrl(split[2]);
            urlInfo.setUrlType(split[3]);
            urlInfo.setAlbum(split[4]);
            urlInfo.setCreated(new Date());
            urlInfoDao.addUrl(urlInfo);
            resp.setContent("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setContent("添加失败");
        }
        return resp;
    }
}
