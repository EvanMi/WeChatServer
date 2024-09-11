package com.yumi.WeChatServer.domain.commands;

public interface CommandConstant {
    String COMMAND_SPLIT = "#";

    String WELCOME_TEXT = """
                    [欢迎关注程序员玉米]
                    目前支持的功能如下:
                    ---------------
                    ?#文章/视频名关键字 搜索历史文章/视频
                    例如 ?#玉米 搜索标题包含玉米的文章/视频
                    ---------------
                    ?help 获取最新指令集
                    ---------------
                    page#合集名#页码 分页查询合集下内容
                    例如 page#大厂文章#1 获取大厂文章下第一页内容
                    ---------------
                    albums 列出所有合集
                    """;
}
