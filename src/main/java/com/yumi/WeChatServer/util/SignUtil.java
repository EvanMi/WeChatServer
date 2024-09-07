package com.yumi.WeChatServer.util;

import com.yumi.WeChatServer.domain.WeiXinInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
    public static boolean checkSignature(String signature, String timestamp,
                                         String nonce, WeiXinInfo info) {
        String[] arr = new String[] {info.getToken(), timestamp, nonce };
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {/*进行sha1加密*/
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);//将加密后的byte转化为16进制字符串，这就是我们自己构造的signature
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;

        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;//进行对比，相同返回true，不同返回false
    }

    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return new String(tempArr);
    }
}
