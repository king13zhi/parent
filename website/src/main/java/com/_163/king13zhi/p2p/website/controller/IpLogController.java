package com._163.king13zhi.p2p.website.controller;

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

	//用户登录验证日志记录
	@RequestMapping("/ipLog")
	public String ipLog(@ModelAttribute("qo") IplogQueryObject qo,Model model) { //@ModelAttribute("qo") 带有model共享数据的功能
		/**
		 * 1.ueryobject 是从页面传递过来的参数,一般用于高级查询等
		 * 2.用了springMVC之后,后台传递参数到前台页面一般通过同名类名和MODEL model的addattribute方法
		 * 3.@ModelAttribute("xx")相当于mode.attribute的作用,这个以前一直以为是只是更改别名.这个应该也是一样的,
		 *   因为只能传递ipLogQueryObject,如果想要传递qo这个变量名的话,那么就只能用@ModelAttribute();
		 */
		qo.setUsername(UserContext.getCurrent().getUsername());
		/**
		 * PageInfo相当于之前的pageResult.
		 */
		PageInfo pageInfo = iIpLogService.queryPageHelper(qo);
		model.addAttribute("pageResult",pageInfo);
		return "ipLog_list";
	}
}
