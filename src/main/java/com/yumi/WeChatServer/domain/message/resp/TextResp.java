package com.yumi.WeChatServer.domain.message.resp;

public class TextResp extends BaseResp {
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
