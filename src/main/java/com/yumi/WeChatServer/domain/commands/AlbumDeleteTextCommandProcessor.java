package com.yumi.WeChatServer.domain.commands;

import com.yumi.WeChatServer.dao.AlbumDao;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static com.yumi.WeChatServer.domain.commands.CommandConstant.COMMAND_SPLIT;

@Component("[delAlbum]")
public class AlbumDeleteTextCommandProcessor extends AdminTextCommandProcessor {
    @Resource
    private AlbumDao albumDao;

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
            albumDao.deleteAlbumByName(split[1]);
            resp.setContent("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setContent("添加失败");
        }
        return resp;
    }
}
