package com.yumi.WeChatServer.util;

import com.yumi.WeChatServer.domain.WeiXinInfo;
import com.yumi.WeChatServer.util.aes.AesException;
import com.yumi.WeChatServer.util.aes.WXBizMsgCrypt;

public class AesUtils {
    public static String descMessage(String encrypt, String msgSignature,
                                     String timestamp, String nonce, WeiXinInfo weiXinInfo) throws AesException {
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
        String token = weiXinInfo.getToken();
        String encodingAesKey = weiXinInfo.getEncodingAESKey();
        String appId = weiXinInfo.getAppId();
        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        return pc.encryptMsg(replyMsg, timestamp, nonce);
    }
}