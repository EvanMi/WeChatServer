package com.yumi.WeChatServer.controller;

import com.alibaba.fastjson2.JSON;
import com.yumi.WeChatServer.domain.WeiXinInfo;
import com.yumi.WeChatServer.domain.message.req.EventRequest;
import com.yumi.WeChatServer.domain.message.req.TextRequest;
import com.yumi.WeChatServer.service.SubscribeEventService;
import com.yumi.WeChatServer.service.TextMessageService;
import com.yumi.WeChatServer.util.AesUtils;
import com.yumi.WeChatServer.util.MessageUtil;
import com.yumi.WeChatServer.util.SignUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Map;

@Controller
@RequestMapping("/core")
public class CoreController {
    @Resource
    private WeiXinInfo weiXinInfo;
    @Resource
    private TextMessageService textMessageService;
    @Resource
    private SubscribeEventService subscribeEventService;

    private static Logger logger = LoggerFactory.getLogger(CoreController.class);

    @GetMapping
    public void goGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        try (PrintWriter out = response.getWriter()) {
            if (SignUtil.checkSignature(signature, timestamp, nonce, weiXinInfo)) {
                out.print(echostr);
                out.flush();
            }
        }
    }

    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String respMessage = "";

        try {
            /* 从请求中获得xml格式的信息。并转化为map类型 */
            Map<String, String> requestMapSecret = MessageUtil
                    .parseXml(request);
            /* 获得解密后的消息正文 */
            String descMessage = AesUtils.descMessage(
                    requestMapSecret.get("Encrypt"),
                    request.getParameter("msg_signature"),
                    request.getParameter("timestamp"),
                    request.getParameter("nonce"), weiXinInfo);
            /* 将明文再次进行xml解析 */
            Map<String, String> requestMap = MessageUtil
                    .parseXml(new StringReader(descMessage));
            /** 获得用户发来的消息类型，并做相应的处理 */
            String messageType = requestMap.get("MsgType");
            logger.info("message: {}", JSON.toJSON(requestMap));
            /*处理不同格式的消息类型开始-------------------------------------------------------*/
            // 用户发来的是文本消息

            if (messageType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                TextRequest textMessage = new TextRequest();
                textMessage.setFromUserName(requestMap.get("FromUserName"));
                textMessage.setToUserName(requestMap.get("ToUserName"));
                textMessage.setMsgType(requestMap.get("MsgType"));
                textMessage.setMsgId(Long.valueOf(requestMap.get("MsgId")));
                textMessage.setCreateTime(Long.valueOf(requestMap.get("CreateTime")));
                textMessage.setContent(requestMap.get("Content"));
                respMessage = textMessageService.processText(textMessage);
            }
            // 用户发来的是图片消息
            else if (messageType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {

            }
            // 用户发来地理位置信息
            else if (messageType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {

            }
            // 用户发来链接消息
            else if (messageType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {

            }
            // 用户发来音频消息
            else if (messageType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {

            }
            /** 事件推送的处理 */
            else if (messageType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.REQ_EVENT_TYPE_SUBSCRIBE)) {
                    logger.info("subscribe~");
                    EventRequest eventRequest = new EventRequest();
                    eventRequest.setFromUserName(requestMap.get("FromUserName"));
                    eventRequest.setToUserName(requestMap.get("ToUserName"));
                    eventRequest.setMsgType(requestMap.get("MsgType"));
                    eventRequest.setCreateTime(Long.valueOf(requestMap.get("CreateTime")));
                    eventRequest.setEvent(requestMap.get("Event"));
                    eventRequest.setEventKey(requestMap.get("EventKey"));
                    eventRequest.setTicket(requestMap.get("Ticket"));
                    respMessage = subscribeEventService.processEvent(eventRequest);
                }
                // 取消订阅
                else if (eventType
                        .equals(MessageUtil.REQ_EVENT_TYPE_UNSUBSCRIBE)) {
                }
                // 点击按钮事件
                else if (eventType.equals(MessageUtil.REQ_EVENT_TYPE_CLICK)) {

                }
            }
            /*处理不同格式的消息类型介绍-------------------------------------------------------*/
            logger.info("respMessage: {}", respMessage);
            respMessage = AesUtils.aescMessage(respMessage,
                    request.getParameter("timestamp"),
                    request.getParameter("nonce"), weiXinInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }

}
