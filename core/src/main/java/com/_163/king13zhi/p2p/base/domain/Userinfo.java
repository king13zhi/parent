package com._163.king13zhi.p2p.base.domain;

import com._163.king13zhi.p2p.base.util.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
public class Userinfo extends BaseDomain {
	private Integer version;
	private Long bitState = 0L;
	private String realName;
	private String idNumber;
	private String phoneNumber;
	private int score; //风控材料的总得分,需要风控师判断,因为是比较所以用int,不然报异常.
	private Long realAuthId; //实名认证的id
	private String email;

	//选择下拉框状态值
	private Systemdictionaryitem incomeGrade;
	private Systemdictionaryitem marriage;
	private Systemdictionaryitem kidCount;
	private Systemdictionaryitem educationBackground;
	private Systemdictionaryitem houseCondition;

	//返回手机绑定的状态
	public boolean getHasBindPhone() {
		return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_BIND_PHONE);
	}

	//返回基本资料认证的状态
	public boolean getIsBasicInfo() {return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_BASIC_INFO);}

	//返回是否实名认证的状态
	public boolean getIsRealAuth() {return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_REAL_AUTH);}

	//返回视频是否认证的状态
	public boolean getIsVedioAuth() {
		return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_VEDIO_AUTH);
	}

	//手机验证后改变状态码(基本都是通用了的)
	public void addState(Long state) {
		this.bitState = BitStatesUtils.addState(this.bitState,state);
	}

	//返回邮箱绑定状态
	public boolean getHasBindEmail(){
		return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_BIND_EMAIL);
	}

	//返回邮箱绑定状态
	public boolean getHasBidRequestProcess(){
		return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
	}

	//移除状态码
	public void removeStae(Long state) {
		this.bitState = BitStatesUtils.removeState(this.bitState,state);
	}

	/**
	 * 获取用户真实名字的隐藏字符串，只显示姓氏
	 *
	 * @param realName
	 *            真实名字
	 * @return
	 */
	public String getAnonymousRealName() {
		if ( StringUtils.hasLength(this.realName)) {
			int len = realName.length();
			String replace = "";
			replace += realName.charAt(0);
			for (int i = 1; i < len; i++) {
				replace += "*";
			}
			return replace;
		}
		return realName;
	}

	/**
	 * 获取用户身份号码的隐藏字符串
	 *
	 * @param idNumber
	 * @return
	 */
	public String getAnonymousIdNumber() {
		if (StringUtils.hasLength(idNumber)) {
			int len = idNumber.length();
			String replace = "";
			for (int i = 0; i < len; i++) {
				if ((i > 5 && i < 10) || (i > len - 5)) {
					replace += "*";
				} else {
					replace += idNumber.charAt(i);
				}
			}
			return replace;
		}
		return idNumber;
	}
}