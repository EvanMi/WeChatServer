package com.yumi.WeChatServer.domain.button;

import java.util.List;

//一级菜单，这个菜单下面包含很多的底层button
public class ComplexButton extends Button {

	private List<Button> sub_button;

	public List<Button> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}
}
