package com._163.king13zhi.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Systemdictionaryitem extends BaseDomain {
	private Long parentId;
	private String title;
	private Byte sequence;

	//前台jsonString表单数据封装
	public String getJsonString(){
		Map<String,Object> param = new HashMap<>();
		param.put("id",id);
		param.put("title",title);
		param.put("parentId",parentId);
		param.put("sequence",sequence);
		return JSON.toJSONString(param);
	}
}