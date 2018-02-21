package com._163.king13zhi.p2p.mgrsite.controller;

import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.service.IRealAuthService;
import com._163.king13zhi.p2p.base.service.IUserFileService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.business.domain.BidRequest;
import com._163.king13zhi.p2p.business.domain.PaymentScheduleDetail;
import com._163.king13zhi.p2p.business.query.BidRequestQueryObject;
import com._163.king13zhi.p2p.business.service.IBidRequestAuditHistoryService;
import com._163.king13zhi.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/26.
 */
@Controller
public class BorrowController {
	@Autowired
	private IBidRequestService bidRequestService;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IRealAuthService realAuthService;
	@Autowired
	private IUserFileService userFileService;
	@Autowired
	private IBidRequestAuditHistoryService bidRequestAuditHistoryService;

	@RequestMapping("/bidrequest_publishaudit_list")
	public String pulishAuditPage(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
		qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
		model.addAttribute("pageResult", bidRequestService.queryPage(qo));
		return "bidrequest/publish_audit";
	}

	@RequestMapping("/bidrequest_publishaudit")
	@ResponseBody
	public AjaxResult publishAudit(Long id, int state, String remark) {
		try {
			bidRequestService.publishAudit(id, state, remark);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}

	@RequestMapping("/borrow_info")
	public String borrowInfoPage(Long id, Model model) {
		//bidRequest
		BidRequest bidRequest = bidRequestService.get(id);
		if ( bidRequest != null ) {
			model.addAttribute("bidRequest", bidRequest);
			//userinfo
			Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
			model.addAttribute("userInfo", userinfo);
			//realAuth
			model.addAttribute("realAuth", realAuthService.get(userinfo.getRealAuthId()));
			//audits
			model.addAttribute("audits", bidRequestAuditHistoryService.queryByBidRequestId(bidRequest.getId()));
			//userFiles
			model.addAttribute("userFiles", userFileService.queryByUserId(bidRequest.getCreateUser().getId()));
		}
		return "bidrequest/borrow_info";
	}

	@RequestMapping("/bidrequest_audit1_list")
	public String audit1Page(@ModelAttribute("qo") BidRequestQueryObject qo,Model model){
		qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
		model.addAttribute("pageResult",bidRequestService.queryPage(qo));
		return "bidrequest/audit1";
	}


	@RequestMapping("/bidrequest_audit1")
	@ResponseBody
	public AjaxResult bidrequestAudit1(Long id, int state, String remark) {
		try {
			bidRequestService.bidrequestAudit1(id, state, remark);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}

	@RequestMapping("/bidrequest_audit2_list")
	public String audit2Page(@ModelAttribute("qo") BidRequestQueryObject qo,Model model){
		qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
		model.addAttribute("pageResult",bidRequestService.queryPage(qo));
		return "bidrequest/audit2";
	}


	@RequestMapping("/bidrequest_audit2")
	@ResponseBody
	public AjaxResult bidrequestAudit2(Long id, int state, String remark) {
		try {
			bidRequestService.bidrequestAudit2(id, state, remark);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true, "验证成功");
	}
}
