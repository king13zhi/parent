package com._163.king13zhi.p2p.business.service.impl;

import com._163.king13zhi.p2p.business.domain.PaymentScheduleDetail;
import com._163.king13zhi.p2p.business.mapper.PaymentScheduleDetailMapper;
import com._163.king13zhi.p2p.business.service.IPaymentScheduleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by kingdan on 2018/1/28.
 */
@Service@Transactional
public class PaymentScheduleDetailServiceImpl implements IPaymentScheduleDetailService {
	@Autowired
	private PaymentScheduleDetailMapper paymentScheduleDetailMapper;

	@Override
	public int save(PaymentScheduleDetail paymentScheduleDetail) {
		return paymentScheduleDetailMapper.insert(paymentScheduleDetail);
	}

	@Override
	public PaymentScheduleDetail get(Long id) {
		return paymentScheduleDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updatePayDate(Long psId, Date payDate) {
		paymentScheduleDetailMapper.updatePayDate(psId,payDate);
	}
}
