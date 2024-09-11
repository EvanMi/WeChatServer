package com.yumi.WeChatServer.dao;

import com.yumi.WeChatServer.domain.po.Album;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void addAlbum(Album album) {
        String sql = "insert into albums (name, created) values (?, ?)";
        this.jdbcTemplate.update(sql, album.getName(), album.getCreated());
    }

    public List<Album> listAllAlbum() {
        String sql = "select * from albums order by created desc";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            Album album = new Album();
            album.setId(rs.getInt("id"));
            album.setName(rs.getString("name"));
            album.setCreated(rs.getDate("created"));
            return album;
        });
    }

    public void deleteAlbumByName(String name) {
        String sql = "delete from albums where name = ?";
        this.jdbcTemplate.update(sql, name);
    }
}
