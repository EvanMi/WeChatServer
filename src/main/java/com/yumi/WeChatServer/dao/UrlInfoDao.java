package com.yumi.WeChatServer.dao;

import com.yumi.WeChatServer.domain.po.UrlInfo;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class UrlInfoDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void addUrl(UrlInfo urlInfo) {
        String sql = "insert into url_info (title, url, url_type, album, created) values (?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, urlInfo.getTitle(), urlInfo.getUrl(),
                urlInfo.getUrlType(), urlInfo.getAlbum(), urlInfo.getCreated());
    }

    public List<UrlInfo> listUrlByTitleKeyword(String keyword) {
        String sql = "select * from url_info where title like ? limit 5";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            UrlInfo urlInfo = new UrlInfo();
            urlInfo.setId(rs.getInt("id"));
            urlInfo.setTitle(rs.getString("title"));
            urlInfo.setUrlType(rs.getString("url_type"));
            urlInfo.setUrl(rs.getString("url"));
            urlInfo.setAlbum(rs.getString("album"));
            urlInfo.setCreated(rs.getDate("created"));
            return urlInfo;
        }, "%" + keyword + "%");
    }

    public void deleteUrlByTitle(String title) {
        String sql = "delete from url_info where title = ?";
        this.jdbcTemplate.update(sql, title);
    }

    public List<UrlInfo> listUrlPageByAlbum(String album, int page) {
        if (page > 0) {
            page = page - 1;
        }
        String sql = "select * from url_info where album like ? limit ?, 5";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            UrlInfo urlInfo = new UrlInfo();
            urlInfo.setId(rs.getInt("id"));
            urlInfo.setTitle(rs.getString("title"));
            urlInfo.setUrlType(rs.getString("url_type"));
            urlInfo.setUrl(rs.getString("url"));
            urlInfo.setAlbum(rs.getString("album"));
            urlInfo.setCreated(rs.getDate("created"));
            return urlInfo;
        }, "%" + album + "%", page);
    }
}
