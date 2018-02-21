package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.query.VideoAuthQueryObject;
import com._163.king13zhi.p2p.base.service.ILogininfoService;
import com._163.king13zhi.p2p.base.service.IVedioAuthService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by kingdan on 2018/1/25.
 */
@Controller
public class VideoAuthAuditController {
	@Autowired
	private IVedioAuthService vedioAuthService;

	@Autowired
	private ILogininfoService logininfoService;

	@RequestMapping("myVideoAuth")
	public String videoAuthList(@ModelAttribute("qo") VideoAuthQueryObject qo, Model model) {
		model.addAttribute("pageResult", this.vedioAuthService.queryPageHelper(qo));
		return "videoAuth/list";
	}

	@RequestMapping("videoAuth_audit")
	@ResponseBody
	public AjaxResult audit(Long applierId, int state, String remark) {
		try {
			this.vedioAuthService.audit(applierId, state, remark);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}

	@RequestMapping("autoComplete")
	@ResponseBody
	public List<Map<String, Object>> autoComplete(String keyword) {
		return this.logininfoService.queryAutoComplete(keyword);
	}
}
