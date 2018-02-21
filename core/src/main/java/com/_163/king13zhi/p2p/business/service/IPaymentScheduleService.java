package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.business.domain.PaymentSchedule;
import com._163.king13zhi.p2p.business.query.PaymentScheduleQueryObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by kingdan on 2018/1/28.
 */
public interface IPaymentScheduleService {
	int save(PaymentSchedule paymentSchedule);
	int update(PaymentSchedule paymentSchedule);
	PaymentSchedule get(Long id);

	PageInfo queryPage(PaymentScheduleQueryObject qo);

	/**
	 * 还款
	 * @param id
	 */
	void returnMoney(Long id);
}
