package com._163.king13zhi.p2p.mgrsite.controller;


import com._163.king13zhi.p2p.base.query.IplogQueryObject;
import com._163.king13zhi.p2p.base.service.IIpLogService;
import com._163.king13zhi.p2p.base.util.UserContext;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kingdan on 2018/1/17.
 */

@Controller
public class IpLogController {
	@Autowired
	private IIpLogService iIpLogService;

	//后台管理登录验证日志记录
	@RequestMapping("/ipLog")
	public String ipLog(@ModelAttribute("qo") IplogQueryObject qo, Model model) {
		qo.setUsername(UserContext.getCurrent().getUsername());
		PageInfo pageInfo = iIpLogService.queryPageHelper(qo);
		model.addAttribute("pageResult",pageInfo);
		return "managerIpLog";
	}
}
