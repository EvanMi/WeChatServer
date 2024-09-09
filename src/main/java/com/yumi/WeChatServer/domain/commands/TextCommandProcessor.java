package com.yumi.WeChatServer.domain.commands;

import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.domain.message.resp.TextResp;

public interface TextCommandProcessor {

    TextResp process(TextRequest req);
}
