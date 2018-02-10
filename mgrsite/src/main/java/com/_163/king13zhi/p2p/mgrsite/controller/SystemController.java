package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.domain.SystemMenu;
import com._163.king13zhi.p2p.base.query.SystemMenuQueryObject;
import com._163.king13zhi.p2p.base.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	/**
	 * 进入mainmenu controller
	 * @return
	 */
	@RequestMapping("/mainMenu")
	public String mainMenu(){
		return "main";
	}

	/**
	 * 动态加载菜单
	 * @param qo
	 * @return
	 */
	@RequestMapping("/loadMenuByParentId")
	@ResponseBody
	public Object loadMenu(@ModelAttribute("qo")SystemMenuQueryObject qo) {
		qo.setParentId(null);
		List<SystemMenu> roots = systemMenuService.loadMenuByParentIdIsNull(qo);
		Map<String,Object> map = new HashMap<>();

		List<SystemMenu> eles = null;
		Map<String,Object> eleMap = new HashMap<>();

		Map<String,Object> urlMap = new HashMap<>();
		for (SystemMenu root : roots) {
			map.put("id",root.getId());
			map.put("text",root.getText());
			map.put("state","open");
			//通过每个父节点菜单动态加载子菜单
			qo.setParentId(root.getId());
			eles = systemMenuService.loadMenuByParentIdIsNull(qo);
			for (SystemMenu ele : eles) {
				eleMap.put("id",ele.getId());
				eleMap.put("text",ele.getText());
				eleMap.put("state","open");
				urlMap.put("url",ele.getUrl());
				eleMap.put("attributes", urlMap);
			}
			System.out.println(eles.toString());
			map.put("children", eles);
		}
		System.out.println(roots.toString());
		return roots;
	}
}
