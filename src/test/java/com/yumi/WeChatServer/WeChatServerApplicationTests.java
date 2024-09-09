package com.yumi.WeChatServer;

import com.yumi.WeChatServer.domain.po.UrlInfo;
import com.yumi.WeChatServer.domain.po.UrlType;
import com.yumi.WeChatServer.dao.UrlInfoDao;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class WeChatServerApplicationTests {

	@Resource
	private UrlInfoDao urlInfoDao;

	@Test
	void contextLoads() {
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setUrl("http://yumi.com");
		urlInfo.setTitle("测试");
		urlInfo.setUrlType(UrlType.ARTICLE);
		urlInfo.setAlbum("default");
		urlInfo.setCreated(new Date());
		urlInfoDao.addUrl(urlInfo);
	}


	 @Test
    public void listUrlInfo() {
		 List<UrlInfo> res = urlInfoDao.listUrlByTitleKeyword("你");
		 System.out.println(res.size());
	 }
}
