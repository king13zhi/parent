package com._163.king13zhi.p2p.business.mapper;

import com._163.king13zhi.p2p.business.domain.PaymentScheduleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface PaymentScheduleDetailMapper {
    int insert(PaymentScheduleDetail paymentScheduleDetail);
    PaymentScheduleDetail selectByPrimaryKey(@Param("id")Long id);

	void updatePayDate(@Param("psId") Long psId, @Param("payDate") Date payDate);
}