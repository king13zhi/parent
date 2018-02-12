package com._163.king13zhi.p2p.base.domain;

import com._163.king13zhi.p2p.base.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Account extends BaseDomain {
	private int version;
	//保留四位精度
	private String tradePassword;
	private BigDecimal usableAmount = BidConst.ZERO; //账户可用余额
	private BigDecimal freezedAmount = BidConst.ZERO; //账户冻结金额
	private BigDecimal unReceiveInterest = BidConst.ZERO; //账户待收利息
	private BigDecimal unReceivePrincipal = BidConst.ZERO; //账户待收本金
	private BigDecimal unReturnAmount = BidConst.ZERO; //账户待还金额

	private BigDecimal remainBorrowLimit = BidConst.INIT_BORROW_LIMIT; //账户剩余授信额度
	private BigDecimal borrowLimit = BidConst.INIT_BORROW_LIMIT; //账户授信额度

	//获取总金额=可用金额+冻结金额+待收本金
	public BigDecimal getTotalAccount(){
		return ((this.usableAmount.add(this.freezedAmount)).add(this.unReceivePrincipal));
	}
}