package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.service.ISystemDictionaryItemService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/23.
 */

@Controller
public class BasicInfoController {
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItemService;

	@RequestMapping("basicInfo")
	public String baseInfo_list(Model model){
		model.addAttribute("userinfo",userinfoService.get(UserContext.getCurrent().getId()));
		model.addAttribute("educationBackgrounds",systemDictionaryItemService.queryListBySn("educationBackground"));
		model.addAttribute("incomeGrades",systemDictionaryItemService.queryListBySn("incomeGrade"));
		model.addAttribute("marriages",systemDictionaryItemService.queryListBySn("marriage"));
		model.addAttribute("kidCounts",systemDictionaryItemService.queryListBySn("kidCount"));
		model.addAttribute("houseConditions",systemDictionaryItemService.queryListBySn("houseCondition"));
		return "userInfo";
	}

	@RequestMapping("basicInfo_save")
	@ResponseBody
	public AjaxResult saveOrUpdate(Userinfo userinfo){
		try {
			userinfoService.basicInfoSave(userinfo);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"保存成功");
	}
}
