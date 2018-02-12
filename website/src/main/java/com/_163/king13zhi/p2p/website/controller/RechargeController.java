package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.business.domain.RechargeOffline;
import com._163.king13zhi.p2p.business.service.IPlatformBankinfoService;
import com._163.king13zhi.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/27.
 */
@Controller
public class RechargeController {
	@Autowired
	private IPlatformBankinfoService platformBankinfoService;
	@Autowired
	private IRechargeOfflineService rechargeOfflineService;


	@RequestMapping("/recharge")
	public String rechargePage(Model model) {
		model.addAttribute("banks", platformBankinfoService.selectAll());
		return "recharge";
	}

	@RequestMapping("/recharge_save")
	@ResponseBody
	public AjaxResult rechargeSave(RechargeOffline rechargeOffline) {
		try {
			rechargeOfflineService.rechargeSave(rechargeOffline);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}
}
