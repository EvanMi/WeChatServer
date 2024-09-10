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

@Component("?")
public class UrlInfoSearchCommandProcessor implements TextCommandProcessor {
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

        if (split.length == 2) {
            //优先把key视为整体
            List<UrlInfo> urlInfos = urlInfoDao.listUrlByTitleKeyword(split[1]);
            if (null == urlInfos || urlInfos.isEmpty()) {
                //整体没有进行分词
                 ConcurrentMap<Integer, UrlInfo> resMap = SegmentUtil.segmentIndex(split[1])
                    .parallelStream()
                    .flatMap(keyword -> urlInfoDao.listUrlByTitleKeyword(keyword).stream())
                    .collect(Collectors.toConcurrentMap(UrlInfo::getId, Function.identity(), (v1, v2) -> v1));
                 urlInfos = new ArrayList<>(resMap.values());
            }
            Collections.sort(urlInfos, Comparator.comparing((UrlInfo u) -> u.getCreated()));
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
            return resp;
        }
        resp.setContent("检查一下你的搜索命令");
        return resp;
    }
}
