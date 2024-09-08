package com.yumi.WeChatServer.util;

import com.thoughtworks.xstream.XStream;
import com.yumi.WeChatServer.domain.message.resp.TextResp;
import jakarta.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    public static final String REQ_MESSAGE_TYPE_SHORT_VIDEO = "shortvideo";
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";
    public static final String REQ_EVENT_TYPE_SUBSCRIBE = "subscribe";
    public static final String REQ_EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    public static final String REQ_EVENT_TYPE_CLICK = "CLICK";
    public static final String REQ_EVENT_TYPE_LOCATION = "LOCATION";
    public static final String REQ_EVENT_TYPE_SCAN = "SCAN";

    public static final String REQ_EVENT_TYPE_VIEW = "VIEW";
    public static Map<String, String> parseXml(HttpServletRequest request)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();//我用的是SAXReader
        Document document = reader.read(inputStream);

        Element root = document.getRootElement();

        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();

        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }

        inputStream.close();
        inputStream = null;

        return map;
    }

    public static Map<String, String> parseXml(StringReader readers) {
        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            InputSource inputSource = new InputSource(readers);
            document = reader.read(inputSource);
        } catch (DocumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Element root = document.getRootElement();

        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();

        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }


    public static String textMessageToXml(TextResp textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }
}
