package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.business.query.RechargeOfflineQueryObject;
import com._163.king13zhi.p2p.business.service.IPlatformBankinfoService;
import com._163.king13zhi.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/27.
 */
@Controller
public class RechargeOfflineController {
	@Autowired
	private IRechargeOfflineService rechargeOfflineService;
	@Autowired
	private IPlatformBankinfoService platformBankinfoService;

	@RequestMapping("/rechargeOffline")
	public String rechargeOfflinePage(@ModelAttribute("qo") RechargeOfflineQueryObject qo, Model model) {
		model.addAttribute("pageResult", rechargeOfflineService.pagePage(qo));
		model.addAttribute("banks", platformBankinfoService.selectAll());
		return "rechargeoffline/list";
	}

	@RequestMapping("/rechargeOffline_audit")
	@ResponseBody
	public AjaxResult rechargeOfflineAudit(Long id, int state, String remark) {
		try {
			rechargeOfflineService.audit(id, state, remark);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}
}
