package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.domain.Systemdictionary;
import com._163.king13zhi.p2p.base.query.SystemdictionaryQueryObject;
import com._163.king13zhi.p2p.base.service.ISystemDictionaryService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import com.github.pagehelper.PageInfo;
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
public class SystemDictionaryController {
	@Autowired
	private ISystemDictionaryService systemDictionaryService;

	/**
	 * 数据字典对应数据获取
	 *
	 * @return
	 */
	@RequestMapping("systemDictionary_list")
	public String systemDictionaryList(@ModelAttribute("qo") SystemdictionaryQueryObject qo, Model model){
		PageInfo pageInfo = systemDictionaryService.queryPageHelper(qo);
		model.addAttribute("pageResult",pageInfo);
		return "systemDictionary_list";
	}

	@RequestMapping("systemDictionary_update")
	@ResponseBody
	public AjaxResult saveOrUpdate(Systemdictionary systemdictionary){
		try {
			systemDictionaryService.saveOrUpdate(systemdictionary);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"保存成功");
	}
}
