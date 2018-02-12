package com._163.king13zhi.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Systemdictionary extends BaseDomain {
	private String sn;
	private String title;

	//前台jsonString表单数据封装
	public String getJsonString(){
		Map<String,Object> param = new HashMap<>();
		param.put("id", id);
		param.put("title",title);
		param.put("sn",sn);
		return JSON.toJSONString(param);
	}
}