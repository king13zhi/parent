package com._163.king13zhi.p2p.business.domain;

import com._163.king13zhi.p2p.base.domain.BaseDomain;
import com._163.king13zhi.p2p.base.domain.Logininfo;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.business.util.CalculatetUtil;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by kingdan on 2018/1/25.
 */
//借款对象
@Setter@Getter
public class BidRequest extends BaseDomain {
	private int version;// 版本号
	private int returnType = 0;// 还款类型(等额本息)
	private int bidRequestType;// 借款类型(信用标)
	private int bidRequestState;// 借款状态
	private BigDecimal bidRequestAmount;// 借款总金额
	private BigDecimal currentRate;// 年化利率
	private BigDecimal minBidAmount;// 最小投標金额
	private int monthes2Return;// 还款月数
	private int bidCount = 0;// 已投标次数(冗余)
	private BigDecimal totalRewardAmount;// 总回报金额(总利息)
	private BigDecimal currentSum = BidConst.ZERO;// 当前已投标总金额
	private String title;// 借款标题
	private String description;// 借款描述
	private String note;// 风控意见
	private Date disableDate;// 招标截止日期
	private int disableDays;// 招标天数
	private Logininfo createUser;// 借款人
	private Date applyTime;// 申请时间
	private Date publishTime;// 发标时间
	private List<Bid> bids = new ArrayList<>();// 针对该借款的投标

	//数据封装
	public String getJsonString() {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("username", createUser.getUsername());
		param.put("title", title);
		param.put("bidRequestAmount", bidRequestAmount);
		param.put("currentRate", currentRate);
		param.put("monthes2Return", monthes2Return);
		param.put("returnType", getReturnTypeDisplay());
		param.put("totalRewardAmount", totalRewardAmount);
		return JSON.toJSONString(param);
	}

	//借款方式中文显示
	public String getReturnTypeDisplay() {
		return this.returnType == BidConst.RETURN_TYPE_MONTH_INTEREST ? "按月到期" : "按月分期";
	}

	//标状态对应的中文显示
	public String getBidRequestStateDisplay() {
		switch (this.bidRequestState) {
			case BidConst.BIDREQUEST_STATE_PUBLISH_PENDING:
				return "待发布";
			case BidConst.BIDREQUEST_STATE_BIDDING:
				return "招标中";
			case BidConst.BIDREQUEST_STATE_UNDO:
				return "已撤销";
			case BidConst.BIDREQUEST_STATE_BIDDING_OVERDUE:
				return "流标";
			case BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1:
				return "满标一审";
			case BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2:
				return "满标二审";
			case BidConst.BIDREQUEST_STATE_REJECTED:
				return "满标审核被拒";
			case BidConst.BIDREQUEST_STATE_PAYING_BACK:
				return "还款中";
			case BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK:
				return "完成";
			case BidConst.BIDREQUEST_STATE_PAY_BACK_OVERDUE:
				return "逾期";
			case BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE:
				return "发标拒绝";
			default:
				return "";
		}
	}

	public BigDecimal getRemainAmount() {
		return this.bidRequestAmount.subtract(this.currentSum);
	}

	public BigDecimal getPersent() {
		return this.currentSum.multiply(CalculatetUtil.ONE_HUNDRED).divide(this.bidRequestAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
	}
}

