package com._163.king13zhi.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by kingdan on 2018/2/2.
 */
@Setter
@Getter
public class SpecialPriceRoom {
	private Long id;
	private String HotelName;
	private BigDecimal salePrice;
	private BigDecimal discountPrice;
	private Float discount;
	private Long parentId;
	private String imagePath;
	private int number;
	private int remark;
}
