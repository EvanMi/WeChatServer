package com.yumi.WeChatServer;

import com.yumi.WeChatServer.domain.po.UrlInfo;
import com.yumi.WeChatServer.domain.po.UrlType;
import com.yumi.WeChatServer.service.UrlInfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class WeChatServerApplicationTests {

	@Resource
	private UrlInfoService urlInfoService;

	@Test
	void contextLoads() {
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setUrl("http://yumi.com");
		urlInfo.setTitle("测试");
		urlInfo.setUrlType(UrlType.ARTICLE);
		urlInfo.setAlbum("default");
		urlInfo.setCreated(new Date());
		urlInfoService.addUrl(urlInfo);
	}

}
