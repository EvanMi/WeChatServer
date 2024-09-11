package com.yumi.WeChatServer.domain.commands;

import com.yumi.WeChatServer.dao.UrlInfoDao;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import com.yumi.WeChatServer.domain.po.UrlInfo;
import com.yumi.WeChatServer.util.SegmentUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.yumi.WeChatServer.domain.commands.CommandConstant.COMMAND_SPLIT;

@Component("page")
public class UrlInfoPageCommandProcessor implements TextCommandProcessor {
    @Resource
    private UrlInfoDao urlInfoDao;

    @Override
    public TextResp process(TextRequest req) {
        String content = req.getContent();
        String[] split = content.split(COMMAND_SPLIT);

        TextResp resp = new TextResp();
        resp.setCreateTime(System.currentTimeMillis() / 1000);
        resp.setFromUserName(req.getToUserName());
        resp.setToUserName(req.getFromUserName());
        resp.setMsgType(req.getMsgType());

        if (split.length == 3) {
            String page = split[2];
            int numPage = 1;
            try {
                numPage = Integer.parseInt(page);
            } catch (Exception e) {
                //ignore
            }
            List<UrlInfo> urlInfos = urlInfoDao.listUrlPageByAlbum(split[1], numPage);
            StringBuilder sb = new StringBuilder();
            for (UrlInfo value : urlInfos) {
                sb.append("[").append(value.getUrlType()).append("] ").append(value.getAlbum()).append("-").append(value.getTitle())
                        .append(" ").append(value.getUrl()).append("\n");
            }
            if (sb.length() == 0) {
                resp.setContent("未搜索到任何信息");
            } else {
                resp.setContent(sb.toString());
            }
        }
        resp.setContent("检查一下你的搜索命令");
        return resp;
    }
}
