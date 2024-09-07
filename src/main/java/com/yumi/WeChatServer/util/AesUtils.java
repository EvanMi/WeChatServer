package com.yumi.WeChatServer.util;

import com.yumi.WeChatServer.domain.WeiXinInfo;
import com.yumi.WeChatServer.util.aes.AesException;
import com.yumi.WeChatServer.util.aes.WXBizMsgCrypt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AesUtils {

    private static ApplicationContext applicationContext;

    /*
     * 这里我直接使用的spring来读取weiInfo对象中的加解密密钥的内容，大家可以不用这样，直接把密钥放在这里就可以了，
     * 也可以放在properties文件中读取更方便
     */

    public static String descMessage(String encrypt, String msgSignature,
                                     String timestamp, String nonce, WeiXinInfo weiXinInfo) throws AesException {
        applicationContext = new ClassPathXmlApplicationContext(
                "config/application-context.xml");
        String token = weiXinInfo.getToken();
        String encodingAesKey = weiXinInfo.getEncodingAESKey();
        String appId = weiXinInfo.getAppId();
        String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
        String fromXML = String.format(format, encrypt);
        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        String result = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
        return result;
    }


    /*加密和解密类似，就不做注释了*/
    public static String aescMessage(String replyMsg, String timestamp,
                                     String nonce, WeiXinInfo weiXinInfo) throws AesException {
        applicationContext = new ClassPathXmlApplicationContext(
                "config/application-context.xml");
        String token = weiXinInfo.getToken();
        String encodingAesKey = weiXinInfo.getEncodingAESKey();
        String appId = weiXinInfo.getAppId();
        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        return pc.encryptMsg(replyMsg, timestamp, nonce);
    }
}