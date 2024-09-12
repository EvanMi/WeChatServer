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

    @Test
    public void testCreateMenu() {
        weiXinUtil.createMenu();
    }

    @Test
    public void testListArticles() {
        String token = "84_wYEwK4h9WalF2yqR8ZzWPxNMA07A6qc3dnVB-xsyikVO7ZubSMqpc18ThxcVH1oGpC2CrwyP_oMHZkACg_kToG81GTCZeGd0e84H2JzOLjJ8sWur32u8bcm7JiMVCHgAIAIPU";
        weiXinUtil.listArticles(1, 20, false, token);
    }

    @Test
    public void testListMaterial() {
        String token = "84_wYEwK4h9WalF2yqR8ZzWPxNMA07A6qc3dnVB-xsyikVO7ZubSMqpc18ThxcVH1oGpC2CrwyP_oMHZkACg_kToG81GTCZeGd0e84H2JzOLjJ8sWur32u8bcm7JiMVCHgAIAIPU";
        weiXinUtil.listMaterial(0, 20, WeiXinUtil.ListMaterialRequest.IMAGE_TYPE, token);
    }
}
