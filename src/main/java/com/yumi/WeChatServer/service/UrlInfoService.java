package com.yumi.WeChatServer.service;

import com.yumi.WeChatServer.domain.po.UrlInfo;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UrlInfoService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void addUrl(UrlInfo urlInfo) {
        String sql = "insert into url_info (title, url, url_type, album, created) values (?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, urlInfo.getTitle(), urlInfo.getUrl(),
                urlInfo.getUrlType(), urlInfo.getAlbum(), urlInfo.getCreated());
    }
}
