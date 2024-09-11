package com.yumi.WeChatServer.service;

import com.yumi.WeChatServer.dao.AlbumDao;
import com.yumi.WeChatServer.domain.message.req.EventRequest;
import com.yumi.WeChatServer.domain.po.Album;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClickEventService {
    @Resource
    private AlbumDao albumDao;

    public String processEvent(EventRequest eventRequest) {
        if ("album".equalsIgnoreCase(eventRequest.getEventKey())) {
            List<Album> albums = albumDao.listAllAlbum();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < albums.size(); i++) {
                sb.append(i + 1).append(": ").append(albums.get(i).getName()).append("\n");
            }
            return sb.toString();
        }
        return "你点了啥?";
    }
}
