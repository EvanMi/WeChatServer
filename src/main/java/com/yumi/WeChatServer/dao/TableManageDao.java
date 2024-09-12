package com.yumi.WeChatServer.dao;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TableManageDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void initDb() {
        String sql1 = """
                    CREATE TABLE url_info (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    title VARCHAR(255),
                    url VARCHAR(255),
                    url_type VARCHAR(255),
                    album VARCHAR(255),
                    created TIMESTAMP
                );
                                """;
        String sql2 = """
                                CREATE TABLE albums (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    created TIMESTAMP NOT NULL
                );
                                """;
        String sql3 = """
                                CREATE TABLE msg_id (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    title VARCHAR(255) NOT NULL,
                    msg_id VARCHAR(255) NOT NULL,
                    created TIMESTAMP NOT NULL
                )
                                """;
        this.jdbcTemplate.update(sql3);
    }
}
