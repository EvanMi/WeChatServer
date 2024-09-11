package com.yumi.WeChatServer.domain.commands;

import com.yumi.WeChatServer.dao.AlbumDao;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import com.yumi.WeChatServer.domain.po.Album;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("albums")
public class AlbumListTextCommandProcessor extends AdminTextCommandProcessor {
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
            List<Album> albums = albumDao.listAllAlbum();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < albums.size(); i++) {
                sb.append(i + 1).append(": ").append(albums.get(i).getName()).append("\n");
            }
            resp.setContent(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setContent("添加失败");
        }
        return resp;
    }
}
