package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.service.IAccountService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.base.util.UserContext;
import com._163.king13zhi.p2p.business.query.PaymentScheduleQueryObject;
import com._163.king13zhi.p2p.business.service.IPaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/30.
 */
@Controller
public class returnMoneyController {
	@Autowired
	private IPaymentScheduleService paymentScheduleService;
	@Autowired
	private IAccountService accountService;

	@RequestMapping("/borrowBidReturn_list")
	public String returnMoneyPage(@ModelAttribute("qo") PaymentScheduleQueryObject qo, Model model) {
		model.addAttribute("account", accountService.getCurrent());
		qo.setUserId(UserContext.getCurrent().getId());
		model.addAttribute("pageResult", paymentScheduleService.queryPage(qo));
		return "returnmoney_list";
	}

	@RequestMapping("/returnMoney")
	@ResponseBody
	public AjaxResult returnMoney(Long id) {
		try {
			paymentScheduleService.returnMoney(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "还款成功");
	}
}
