package com._163.king13zhi.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by kingdan on 2018/1/17.
 */
@Setter@Getter
public class AjaxResult {
	private boolean success = false;
	private String msg;

	public AjaxResult(String msg) {
		this.msg = msg;
	}

	public AjaxResult(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}
}
