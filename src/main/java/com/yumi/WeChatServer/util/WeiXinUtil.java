package com.yumi.WeChatServer.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yumi.WeChatServer.domain.button.Button;
import com.yumi.WeChatServer.domain.button.ButtonType;
import com.yumi.WeChatServer.domain.button.CommonButton;
import com.yumi.WeChatServer.domain.button.Menu;
import com.yumi.WeChatServer.domain.po.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WeiXinUtil {
    public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static String user_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public static String uni_pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static String jpapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=TOKEN&type=jsapi";

    @Value("${com.yumi.wei.xin.appId}")
    private String appId;
    @Value("${com.yumi.wei.xin.appSecret}")
    private String appSecret;

    private ConcurrentHashMap<String, AccessToken> tokenMap = new ConcurrentHashMap<>();

    //获取accesstoken
    public AccessToken getAccessToken() {
        AccessToken accessToken = tokenMap.computeIfAbsent("ACCESS_TOKEN", key -> {
            String requestUrl = access_token_url.replace("APPID", appId)
                    .replace("APPSECRET", appSecret);
            // 创建HttpGet请求
            HttpGet request = new HttpGet(requestUrl);
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(request)) {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 将响应实体转换为字符串并打印
                    String result = EntityUtils.toString(entity);
                    JSONObject jsonObject = JSON.parseObject(result);
                    AccessToken res = new AccessToken();
                    res.setToken(jsonObject.getString("access_token"));
                    res.setExpireIn(jsonObject.getInteger("expires_in"));
                    res.setExpireAt(System.currentTimeMillis() + (res.getExpireIn() - 1000) * 1000L);
                    return res;
                }
                throw new RuntimeException("返回结果为空");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        if (System.currentTimeMillis() - accessToken.getExpireAt() <= 0) {
            tokenMap.remove("ACCESS_TOKEN");
        }
        return accessToken;
    }

    public void createMenu() {
        Menu menu = new Menu();
        List<Button> buttons = new ArrayList<>();
        CommonButton albumsButton = new CommonButton();
        albumsButton.setKey("album");
        albumsButton.setType(ButtonType.CLICK);
        albumsButton.setName("合集列表");
        buttons.add(albumsButton);

        CommonButton groupButton = new CommonButton();
        groupButton.setKey("group");
        groupButton.setType(ButtonType.CLICK);
        groupButton.setName("加群");
        buttons.add(groupButton);


        CommonButton nothingButton = new CommonButton();
        nothingButton.setKey("nothing");
        nothingButton.setType(ButtonType.CLICK);
        nothingButton.setName("无事发生");
        buttons.add(nothingButton);

        AccessToken accessToken = getAccessToken();
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken.getToken());
        System.out.println(url);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建POST请求
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(JSON.toJSONString(menu)));
            // 发送请求并获取响应
            try (CloseableHttpResponse response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
