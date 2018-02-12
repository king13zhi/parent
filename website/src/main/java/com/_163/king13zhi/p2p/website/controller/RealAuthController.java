package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.domain.RealAuth;
import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.service.IRealAuthService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.base.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by kingdan on 2018/1/23.
 */
@Controller
public class RealAuthController {
	@Autowired
	private IRealAuthService realAuthService;
	@Value("${file.path}")
	private String filePath;
	@Autowired
	private IUserinfoService userinfoService;

	@RequestMapping("/realAuth")
	public String realAuthPage(Model model){
		//获取userinfo对象,判断当前用户是否拥有实名认证状态码
		Userinfo userinfo = userinfoService.getCurrent();
		if (userinfo.getIsRealAuth()) {
			//如果有,根据userinfo中的realauthid查询对应的实名认证对象跳转到realauth_result.ftl页面
			RealAuth realAuth = realAuthService.get(userinfo.getRealAuthId());
			model.addAttribute("realAuth",realAuth);
			model.addAttribute("auditing",false);
			return "realAuth_result";
		} else {
			//如果没有
			//判断userinfo对象中的realauthid是否为空
			if (userinfo.getRealAuthId() == null) {
				//如果为空,此时跳转到申请的页面realauth.ftl页面
				return "realAuth_list";
			}else {
				//如果不为null,此时跳转到realAuth_result.ftl页面中
				model.addAttribute("auditing",true);
				return "realAuth_result";
			}
		}
	}

	@RequestMapping("/realAuth_save")
	@ResponseBody
	public AjaxResult realAuthSave(RealAuth realAuth) {
		try {
			realAuthService.realAuthSave(realAuth);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"保存成功");
	}

	@RequestMapping("/uploadImage")
	@ResponseBody
	public String uploadImage(MultipartFile image) {
		return UploadUtil.upload(image,filePath);
	}
}
