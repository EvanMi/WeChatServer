package com.yumi.WeChatServer.util;

import com.alibaba.fastjson2.JSON;
import com.yumi.WeChatServer.domain.po.AccessToken;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeiXinUtilTests {

    @Resource
    private WeiXinUtil weiXinUtil;


    @Test
    public void getAccessToken() {
        for (int i = 0; i < 10; i++) {
            AccessToken accessToken = weiXinUtil.getAccessToken();
            System.out.println(JSON.toJSONString(accessToken));
        }
    }
}
