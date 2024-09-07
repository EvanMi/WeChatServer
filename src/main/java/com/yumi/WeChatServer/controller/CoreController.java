package com.yumi.WeChatServer.controller;

import com.yumi.WeChatServer.domain.WeiXinInfo;
import com.yumi.WeChatServer.util.SignUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/core")
public class CoreController {
    @Resource
    private WeiXinInfo weiXinInfo;

    @GetMapping
    public void goGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        try(PrintWriter out = response.getWriter()) {
            if (SignUtil.checkSignature(signature, timestamp, nonce, weiXinInfo)) {
                out.print(echostr);
                out.flush();
            }
        }
    }

}
