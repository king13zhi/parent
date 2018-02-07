package com._163.king13zhi.p2p.base.controller;

import com._163.king13zhi.p2p.base.domain.SystemMenu;
import com._163.king13zhi.p2p.base.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingdan on 2018/2/7.
 */
@Controller
public class SystemController {
	@Autowired
	private ISystemMenuService systemMenuService;

	@RequestMapping("/loadMenuByParentId")
	@ResponseBody
	public Object loadMenu() {
		List<SystemMenu> roots = systemMenuService.loadMenuByParentIdIsNull(null);
		Map<String,Object> map = new HashMap<>();

		List<SystemMenu> eles = null;
		Map<String,Object> eleMap = new HashMap<>();

		Map<String,Object> urlMap = new HashMap<>();
		for (SystemMenu root : roots) {
			map.put("id",root.getId());
			map.put("text",root.getText());
			map.put("state","open");
			eles = systemMenuService.loadMenuByParentIdIsNull(root.getId());
			for (SystemMenu ele : eles) {
				eleMap.put("id",ele.getId());
				eleMap.put("text",ele.getText());
				eleMap.put("state","open");
				urlMap.put("url",ele.getUrl());
				eleMap.put("attributes", urlMap);
			}
			map.put("children", eles);
		}
		return roots;
	}
}
