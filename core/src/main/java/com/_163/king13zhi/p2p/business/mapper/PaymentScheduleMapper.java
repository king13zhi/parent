package com._163.king13zhi.p2p.business.mapper;

import com._163.king13zhi.p2p.business.domain.PaymentSchedule;
import com._163.king13zhi.p2p.business.query.PaymentScheduleQueryObject;

import java.util.List;

public interface PaymentScheduleMapper {
    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PaymentSchedule record);

	List pagePage(PaymentScheduleQueryObject qo);

	List<PaymentSchedule> queryByBidRequestId(Long bidRequestId);

}