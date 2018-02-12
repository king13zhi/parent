package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.domain.Userinfo;
import com._163.king13zhi.p2p.base.service.IAccountService;
import com._163.king13zhi.p2p.base.service.IRealAuthService;
import com._163.king13zhi.p2p.base.service.IUserFileService;
import com._163.king13zhi.p2p.base.service.IUserinfoService;
import com._163.king13zhi.p2p.base.util.AjaxResult;
import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.base.util.UserContext;
import com._163.king13zhi.p2p.business.domain.BidRequest;
import com._163.king13zhi.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * Created by kingdan on 2018/1/22.
 */
@Controller
public class BorrowIndexController {
	@Autowired
	private IUserinfoService userinfoService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IRealAuthService realAuthService;

	@Autowired
	private IUserFileService userFileService;

	@Autowired
	private IBidRequestService bidRequestService;

	@RequestMapping("/userBorrow")
	public String borrowIndex(Model model) {
		//判断是否有登录,如果登录了跳转到ftl,否则跳转到html页面中
		if (UserContext.getCurrent() == null) {
			return "redirect:borrow.html";
		}
		//共享account到前台页面
		model.addAttribute("account",accountService.getCurrent());
		//共享userinfo到前台页面
		model.addAttribute("userinfo",userinfoService.getCurrent());
		//共享creditBorrowScore到前台页面
		model.addAttribute("creditBorrowScore", BidConst.CREDIT_BORROW_SCORE);
		return "borrow";
	}

	@RequestMapping("borrowInfo")
	public String gotoApplyPage(Model model) {
		if ( UserContext.getCurrent() != null ) {
			//1.判断是否登录,判断是否满足借款的条件
			Userinfo userinfo = userinfoService.getCurrent();
			if ( bidRequestService.canApplyBorrow(userinfo) ) {
				//3.判断用户是否有借款的流程正在审核
				if ( !userinfo.getHasBidRequestProcess() ) {
					//进入申请的页面
					//minBidRequestAmount   最小的借款金额
					model.addAttribute("minBidRequestAmount",BidConst.SMALLEST_BIDREQUEST_AMOUNT);
					//account  账户信息
					model.addAttribute("account",accountService.getCurrent());
					//minBidAmount  系统最小的投标金额
					model.addAttribute("minBidAmount",BidConst.SMALLEST_BID_AMOUNT);
					return "borrow_apply";
				} else {
					//进入结果的页面
					return "borrow_apply_result";
				}
			}
		}
		return "redirect:/borrow";
	}

	@RequestMapping("borrow_apply")
	public String apply(BidRequest bidRequest) {
		this.bidRequestService.apply(bidRequest);
		return "redirect:/borrowInfo.do";
	}

	@RequestMapping("borrow_info")
	public String gotoBorrowInfoPage(Long id, Model model) {
		//bidRequest
		BidRequest bidRequest = bidRequestService.get(id);
		if ( bidRequest != null ) {
			model.addAttribute("bidRequest", bidRequest);
			//userinfo
			Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
			model.addAttribute("userInfo", userinfo);
			//realAuth
			model.addAttribute("realAuth", realAuthService.get(userinfo.getRealAuthId()));
			//userFiles
			model.addAttribute("userFiles", userFileService.queryByUserId(bidRequest.getCreateUser().getId()));

			//判断是否登录
			if ( UserContext.getCurrent() == null ) {
				model.addAttribute("self",false);
			}else {
				//判断当前用户是否为借款人
				if ( UserContext.getCurrent().getId().equals(bidRequest.getCreateUser().getId()) ) {
					model.addAttribute("self",true);
				}else {
					//不是借款人则是投资人
					model.addAttribute("self",false);
					model.addAttribute("account",accountService.getCurrent());
				}
			}
		}
		return "borrow_info";
	}

	@RequestMapping("borrow_bid")
	@ResponseBody
	public AjaxResult bid(Long bidRequestId, BigDecimal amount) {
		try {
			this.bidRequestService.bid(bidRequestId, amount);
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"验证成功");
	}
}
