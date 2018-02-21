package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.base.domain.Account;
import com._163.king13zhi.p2p.base.service.IAccountService;
import com._163.king13zhi.p2p.base.util.UserContext;
import com._163.king13zhi.p2p.business.domain.RechargeOffline;
import com._163.king13zhi.p2p.business.mapper.RechargeOfflineMapper;
import com._163.king13zhi.p2p.business.query.RechargeOfflineQueryObject;
import com._163.king13zhi.p2p.business.service.IAccountFlowService;
import com._163.king13zhi.p2p.business.service.IRechargeOfflineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by kingdan on 2018/1/27.
 */
@Service@Transactional
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
	@Autowired
	private RechargeOfflineMapper rechargeOfflineMapper;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountFlowService accountFlowService;

	@Override
	public int save(RechargeOffline rechargeOffline) {
		return rechargeOfflineMapper.insert(rechargeOffline);
	}

	@Override
	public int update(RechargeOffline rechargeOffline) {
		return rechargeOfflineMapper.updateByPrimaryKey(rechargeOffline);
	}

	@Override
	public RechargeOffline get(Long id) {
		return rechargeOfflineMapper.selectByPrimaryKey(id);
	}

	@Override
	public void rechargeSave(RechargeOffline rechargeOffline) {
		//充值的业务判断
		RechargeOffline ro = new RechargeOffline();
		ro.setApplier(UserContext.getCurrent());
		ro.setApplyTime(new Date());
		ro.setTradeCode(rechargeOffline.getTradeCode());
		ro.setBankInfo(rechargeOffline.getBankInfo());
		ro.setState(RechargeOffline.STATE_NORMAL);
		ro.setTradeTime(rechargeOffline.getTradeTime());
		ro.setNote(rechargeOffline.getNote());
		ro.setAmount(rechargeOffline.getAmount());
		this.save(ro);
	}

	@Override
	public PageInfo pagePage(RechargeOfflineQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
		List list = rechargeOfflineMapper.pagePage(qo);
		return new PageInfo(list);
	}

	@Override
	public void audit(Long id, int state, String remark) {
		RechargeOffline ro = this.get(id);
		if ( ro != null && ro.getState() == RechargeOffline.STATE_NORMAL ) {
			//根据id读取审核对象,判读处于待审核状态
			ro.setAuditor(UserContext.getCurrent());
			ro.setAuditTime(new Date());
			ro.setRemark(remark);
			if ( state == RechargeOffline.STATE_PASS ) {
				//设置相关的属性
				ro.setState(RechargeOffline.STATE_PASS);
				//审核成功
				Account applierAccount = accountService.get(ro.getApplier().getId());
				//获取申请人的账户,用户金额管理
				applierAccount.setUsableAmount(applierAccount.getUnReturnAmount().add(ro.getAmount()));
				accountService.update(applierAccount);
				//生成充值成功的流水
				accountFlowService.createRechargeOfflineFlow(applierAccount,ro.getAmount());
			}else{
				//审核失败
				ro.setState(RechargeOffline.STATE_REJECT);
			}
			this.update(ro);
		}
	}
}
