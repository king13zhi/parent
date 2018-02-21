package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.business.domain.PlatformBankinfo;
import com._163.king13zhi.p2p.business.query.PlatformBankinfoQueryObject;
import com._163.king13zhi.p2p.business.service.IPlatformBankinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kingdan on 2018/1/27.
 */
@Controller
public class PlatformBankinfoController {
	@Autowired
	private IPlatformBankinfoService platformBankinfoService;

	@RequestMapping("/companyBank_list")
	public String platformBankinfoPage(@ModelAttribute("qo") PlatformBankinfoQueryObject qo, Model model){
		model.addAttribute("pageResult",platformBankinfoService.queryPage(qo));
		return "platformbankinfo/list";
	}

	@RequestMapping("/companyBank_update")
	public String saveOrUpdate(PlatformBankinfo platformBankinfo) {
		platformBankinfoService.saveOrUpdate(platformBankinfo);
		return "redirect:companyBank_list"; //重新加载一次页面
	}
}
