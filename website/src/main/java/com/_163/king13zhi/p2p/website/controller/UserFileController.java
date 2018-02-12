package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.domain.UserFile;
import com._163.king13zhi.p2p.base.service.ISystemDictionaryItemService;
import com._163.king13zhi.p2p.base.service.IUserFileService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.base.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by kingdan on 2018/1/23.
 */

@Controller
public class UserFileController {
	@Autowired
	private IUserFileService userFileService;
	@Value("${file.path}")
	private String filepath;
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItemService;

	@RequestMapping("userFile")
	public String baseInfo_list(Model model) {
		List<UserFile> unselectFileTypeList = userFileService.selectFileTypeByCondition(false);
		if ( unselectFileTypeList.size() > 0 ) {
			model.addAttribute("userFiles", unselectFileTypeList);
			model.addAttribute("fileTypes", systemDictionaryItemService.queryListBySn("userFileType"));
			return "userFiles_commit";
		} else {
			//userFiles:当前用户上传了图片,但是没有选择风控类型记录的集合
			List<UserFile> selectFileTypeList = userFileService.selectFileTypeByCondition(true);
			model.addAttribute("userFiles", selectFileTypeList);
			return "userFiles";
		}
	}

	@RequestMapping("/userFileUpload")
	@ResponseBody
	public String userFileUpload(MultipartFile file) {
		System.out.println(file);
		String imgPath = UploadUtil.upload(file, filepath);
		userFileService.apply(imgPath);
		return imgPath;
	}

	@RequestMapping("/userFile_selectType")
	@ResponseBody
	public AjaxResult selectType(Long[] id, Long[] fileType) {
		try {
			userFileService.selectType(id, fileType);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}
}



















