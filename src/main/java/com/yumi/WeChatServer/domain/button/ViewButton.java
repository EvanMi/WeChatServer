package com.yumi.WeChatServer.domain.button;
//最底层的button，用来跳转到指定的页面
public class ViewButton extends Button{

	private String type;//类型
	private String url;//url
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}



}
