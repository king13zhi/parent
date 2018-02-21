package com._163.king13zhi.p2p.business.domain;

import com._163.king13zhi.p2p.base.domain.BaseDomain;
import com._163.king13zhi.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by kingdan on 2018/1/28.
 */
@Setter@Getter
public class SystemAccount extends BaseDomain {
	private int version;
	private BigDecimal usableAmount = BidConst.ZERO;//账户可用余额
	private BigDecimal freezedAmount = BidConst.ZERO;//账户冻结金额
}
