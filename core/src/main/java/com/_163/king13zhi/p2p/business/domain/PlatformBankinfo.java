package com._163.king13zhi.p2p.business.domain;

import com._163.king13zhi.p2p.base.domain.BaseDomain;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingdan on 2018/1/27.
 */
@Setter
@Getter
public class PlatformBankinfo extends BaseDomain {
	private String bankName;    //银行名称
	private String accountNumber;  //银行账号
	private String bankForkname;    //支行名称
	private String accountName; //开户人姓名

	public String getJsonString() {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("bankName", bankName);
		param.put("accountName", accountName);
		param.put("accountNumber", accountNumber);
		param.put("bankForkname", bankForkname);
		return JSON.toJSONString(param);
	}
}
