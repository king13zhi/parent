package com._163.king13zhi.p2p.business.domain;

import com._163.king13zhi.p2p.base.domain.BaseAuthDomain;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingdan on 2018/1/27.
 */
@Setter@Getter
public class RechargeOffline  extends BaseAuthDomain{
	private PlatformBankinfo bankInfo;//银行卡信息
	private String tradeCode;//交易号
	private BigDecimal amount;//充值金额
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date tradeTime;//充值时间
	private String note;//充值说明

	public String getJsonString() {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("username",applier.getUsername());
		param.put("tradeCode", tradeCode);
		param.put("amount", amount);
		param.put("tradeTime", new SimpleDateFormat("yyyy-MM-dd").format(tradeTime));
		return JSON.toJSONString(param);
	}

}
