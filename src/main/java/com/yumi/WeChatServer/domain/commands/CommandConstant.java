package com.yumi.WeChatServer.domain.commands;

public interface CommandConstant {
    String COMMAND_SPLIT = "#";

    String WELCOME_TEXT = """
                    [欢迎关注程序员玉米]
                    目前支持的功能如下:
                    ?#文章/视频名关键字 搜索历史文章/视频
                    例如 ?#玉米 搜索标题包含玉米的文章/视频
                    ?help 获取最新指令集
                    """;
}
