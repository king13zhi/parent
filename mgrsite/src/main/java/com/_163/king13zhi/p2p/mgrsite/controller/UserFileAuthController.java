package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.query.UserFileQueryObject;
import com._163.king13zhi.p2p.base.service.IUserFileService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/25.
 */
@Controller
public class UserFileAuthController {
	@Autowired
	private IUserFileService userFileService;

	@RequestMapping("/userFileAuth")
	public String userFileAuthPage(@ModelAttribute("qo") UserFileQueryObject qo, Model model) {
		qo.setSelectFileType(true);
		model.addAttribute("pageResult", userFileService.queryPage(qo));
		return "userFileAuth/list";
	}

	//认证材料审核
	@RequestMapping("/userFile_audit")
	@ResponseBody
	public AjaxResult audit(Long id,int state,int score,String remark) {
		try {
			userFileService.audit(id,state,score,remark);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}
}
