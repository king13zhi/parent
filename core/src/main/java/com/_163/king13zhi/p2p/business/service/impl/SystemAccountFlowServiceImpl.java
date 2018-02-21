package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.business.domain.SystemAccount;
import com._163.king13zhi.p2p.business.domain.SystemAccountFlow;
import com._163.king13zhi.p2p.business.mapper.SystemAccountFlowMapper;
import com._163.king13zhi.p2p.business.service.ISystemAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kingdan on 2018/1/28.
 */
@Service@Transactional
public class SystemAccountFlowServiceImpl implements ISystemAccountFlowService{
	@Autowired
	private SystemAccountFlowMapper systemAccountFlowMapper;

	@Override
	public int save(SystemAccountFlow systemAccountFlow) {
		return systemAccountFlowMapper.insert(systemAccountFlow);
	}

	@Override
	public void createGainAccountManagermentChargeFlow(SystemAccount systemAccount, BigDecimal amount) {
		createFlow(systemAccount,amount, BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE ,"收取用户借款手续费: "+amount+"元");
	}

	@Override
	public void createGainInterestManagerChargeFlow(SystemAccount systemAccount, BigDecimal amount) {
		createFlow(systemAccount,amount, BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE ,"抽取利息管理费费: "+amount+"元");
	}

	private void createFlow(SystemAccount systemAccount, BigDecimal amount, int actionType, String remark) {
		SystemAccountFlow flow = new SystemAccountFlow();
		flow.setActionTime(new Date());
		flow.setActionType(actionType);
		flow.setAmount(amount);
		flow.setFreezedAmount(systemAccount.getFreezedAmount());
		flow.setUsableAmount(systemAccount.getUsableAmount());
		flow.setRemark(remark);
		this.save(flow);
	}


}
