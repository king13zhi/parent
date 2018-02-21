package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.domain.Systemdictionaryitem;
import com._163.king13zhi.p2p.base.query.SystemdictionaryitemQueryObject;
import com._163.king13zhi.p2p.base.service.ISystemDictionaryItemService;
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
public class SystemDictionaryItemController {
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItemService;
	@Autowired
	private ISystemDictionaryService systemDictionaryService;
	/**
	 * 数据字典明细数据获取
	 * @param qo
	 * @param model
	 * @return
	 */
	@RequestMapping("systemDictionaryItem_list")
	public String systemDictionaryItemList(@ModelAttribute("qo") SystemdictionaryitemQueryObject qo, Model model){
		model.addAttribute("systemDictionaryGroups",systemDictionaryService.selectAll());
		PageInfo pageInfo = systemDictionaryItemService.queryPageHelper(qo);
		model.addAttribute("pageResult",pageInfo);
		return "systemDictionaryItem_list";
	}

	@RequestMapping("/systemDictionaryItem_update")
	@ResponseBody
	public AjaxResult saveOrUpdate(Systemdictionaryitem systemdictionaryitem) {
		try {
			if (systemdictionaryitem.getParentId() == null) {
				return new AjaxResult("请选择数据字典分类");
			}
			systemDictionaryItemService.saveOrUpdate(systemdictionaryitem);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"保存成功");
	}

}
