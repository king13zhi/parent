package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.query.RealAuthQueryObject;
import com._163.king13zhi.p2p.base.service.IRealAuthService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/23.
 */
@Controller
public class RealAuthAuditController {
	@Autowired
	private IRealAuthService realAuthService;

	@RequestMapping("/realAuth")
	public String realAuthPage(@ModelAttribute("qo") RealAuthQueryObject qo , Model model) {
		model.addAttribute("pageResult",realAuthService.queryPage(qo));
		return "/realAuth/list";
	}

	//实名认证审核
	@RequestMapping("realAuth_audit")
	@ResponseBody
	public AjaxResult audit(Long id,int state,String remark) {
		try {
			realAuthService.audit(id,state,remark);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"保存成功");
	}

}
