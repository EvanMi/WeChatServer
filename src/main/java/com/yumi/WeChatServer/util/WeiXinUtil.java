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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WeiXinUtil {
    private static Logger logger = LoggerFactory.getLogger(WeiXinUtil.class);
    public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static String user_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public static String uni_pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static String jpapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=TOKEN&type=jsapi";
    public static String list_articles_url = "https://api.weixin.qq.com/cgi-bin/freepublish/batchget?access_token=ACCESS_TOKEN";
    public static String list_material_url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
    public static String list_comments_url = "https://api.weixin.qq.com/cgi-bin/comment/list?access_token=ACCESS_TOKEN";
    @Value("${com.yumi.wei.xin.appId}")
    private String appId;
    @Value("${com.yumi.wei.xin.appSecret}")
    private String appSecret;

    private ConcurrentHashMap<String, AccessToken> tokenMap = new ConcurrentHashMap<>();


    public static class ListMaterialRequest {
        public static String IMAGE_TYPE = "image";
        public static String VIDEO_TYPE = "video";
        public static String VOICE_TYPE = "voice";
        public static String NEWS_TYPE = "news";

        private int offset;
        private int count;
        private String type;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public void listMaterial(int offset, int count, String type, String accessToken) {
        accessToken = accessToken == null ? getAccessToken().getToken() : accessToken;
        String url = list_material_url.replace("ACCESS_TOKEN", accessToken);
        logger.info("url: {}", url);
        ListMaterialRequest listMaterialRequest = new ListMaterialRequest();
        listMaterialRequest.setOffset(offset);
        listMaterialRequest.setCount(count);
        listMaterialRequest.setType(type); ;
        httpPost(url, listMaterialRequest);
    }

    public static class ListCommentsRequest {
        private int msg_data_id;
        private int index = 0;
        private int begin;
        private int count;
        private int type = 0;

        public int getMsg_data_id() {
            return msg_data_id;
        }

        public void setMsg_data_id(int msg_data_id) {
            this.msg_data_id = msg_data_id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getBegin() {
            return begin;
        }

        public void setBegin(int begin) {
            this.begin = begin;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public void listComments(int msgDataId, int index, int begin, int count, int type, String accessToken) {
        accessToken = accessToken == null ? getAccessToken().getToken() : accessToken;
        String url = list_comments_url.replace("ACCESS_TOKEN", accessToken);
        logger.info("url: {}", url);
        ListCommentsRequest listCommentsRequest = new ListCommentsRequest();
        listCommentsRequest.setMsg_data_id(msgDataId);
        listCommentsRequest.setIndex(index);
        listCommentsRequest.setBegin(begin);
        listCommentsRequest.setCount(count);
        listCommentsRequest.setType(type);
        httpPost(url, listCommentsRequest);
    }

    public static class ListArticlesRequest {
        private int offset;
        private int count;
        private int no_content;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getNo_content() {
            return no_content;
        }

        public void setNo_content(int no_content) {
            this.no_content = no_content;
        }
    }

    public void listArticles(int offset, int count, boolean needContent, String accessToken) {
        accessToken = accessToken == null ? getAccessToken().getToken() : accessToken;
        String url = list_articles_url.replace("ACCESS_TOKEN", accessToken);
        logger.info("url: {}", url);
        ListArticlesRequest listArticlesRequest = new ListArticlesRequest();
        listArticlesRequest.setOffset(offset);
        listArticlesRequest.setCount(count);
        listArticlesRequest.setNo_content(needContent ? 0 : 1) ;
        httpPost(url, listArticlesRequest);
    }

    private String httpPost(String url, Object param) {
        String res = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建POST请求
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(JSON.toJSONString(param)));
            // 发送请求并获取响应
            try (CloseableHttpResponse response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("responseBody: {}", responseBody);
                res = responseBody;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

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
        httpPost(url, menu);
    }

}
