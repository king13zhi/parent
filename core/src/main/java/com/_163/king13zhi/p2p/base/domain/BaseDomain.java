package com._163.king13zhi.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by kingdan on 2018/1/17.
 */
@Setter@Getter
public class BaseDomain implements Serializable {
	//protected 本包和子类共享属性,这个要注意.
	protected Long id;
}
