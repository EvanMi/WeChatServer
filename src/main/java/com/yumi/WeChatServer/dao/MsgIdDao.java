package com.yumi.WeChatServer.dao;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MsgIdDao {
    private static final String NOT_TITLE = "xxxyyyxxxyyy";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void addMsgIdWithoutTitle(String msgId) {
        String sql = "insert into msg_id (title, msg_id, created) values (?, ?, ?)";
        this.jdbcTemplate.update(sql, NOT_TITLE, msgId, new Date());
    }

    public void tryBindTitleWithMsgId(String title) {
        String sql = "update msg_id set title = ? where title = '"+ NOT_TITLE +"'";
        this.jdbcTemplate.update(sql, title);
    }

    public String getMsgIdByTitle(String title) {
        String sql = "select * from msg_id where title like ?";
        List<String> msgIdList = this.jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("msg_id") + ":"
                + rs.getString("title"), "%" + title + "%");
        StringBuilder sb = new StringBuilder();
        for (String msgId : msgIdList) {
            sb.append(msgId).append("\n");
        }
        return sb.toString();
    }
}
