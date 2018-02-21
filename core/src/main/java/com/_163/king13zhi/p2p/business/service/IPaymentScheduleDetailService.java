package com._163.king13zhi.p2p.business.service;

import com._163.king13zhi.p2p.business.domain.PaymentScheduleDetail;

import java.util.Date;

/**
 * Created by kingdan on 2018/1/28.
 */
public interface IPaymentScheduleDetailService {
	int save(PaymentScheduleDetail paymentScheduleDetail);
	PaymentScheduleDetail get(Long id);

	void updatePayDate(Long id, Date payDate);
}
