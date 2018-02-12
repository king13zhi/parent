package com._163.king13zhi.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingdan on 2018/1/24.
 */
@Setter
@Getter
public class UserFile extends BaseAuthDomain {
	private String image; //风控材料地址
	private int score;//风控材料分数
	private Systemdictionaryitem fileType; //风控材料类型

	//前台jsonString表单数据封装
	public String getJsonString() {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("applier", applier.getUsername());
		param.put("fileType", fileType.getTitle()); //要设置相对应的这个属性的子属性有值,不然前台报错.
		param.put("image", image);
		return JSON.toJSONString(param);
	}
}
