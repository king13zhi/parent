package com._163.king13zhi.p2p.base.query;

import com._163.king13zhi.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by kingdan on 2018/1/20.
 */
@Setter
@Getter
public class IplogQueryObject extends QueryObject {
	private Date beginDate;
	private Date endDate;
	private int state = -1;
	private String username;

	//日期格式在页面显示用DateFormat进行格式化
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return DateUtil.getEndDate(endDate);
	}

	public String getUsername() {
		return StringUtils.hasLength(username) ? username : null;
	}
}
