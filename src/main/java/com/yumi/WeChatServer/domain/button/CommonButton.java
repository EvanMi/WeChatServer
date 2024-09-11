package com.yumi.WeChatServer.domain.button;

public class CommonButton extends Button {

	private String type;//类型
	private String key;//key值，传入后台后就是根据key值来判断是哪个button被点击了

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
