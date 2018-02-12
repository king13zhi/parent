package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.business.query.BidRequestQueryObject;
import com._163.king13zhi.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kingdan on 2018/1/27.
 */
@Controller
public class InvestController {
	@Autowired
	private IBidRequestService bidRequestService;

	@RequestMapping("/invest")
	public String inverstPage(){
		return "invest";
	}

	@RequestMapping("/invest_list")
	public String investList(BidRequestQueryObject qo, Model model){
		qo.setStates(new int[] {
				BidConst.BIDREQUEST_STATE_BIDDING,BidConst.BIDREQUEST_STATE_PAYING_BACK,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
		qo.setOrderByCondition("ORDER BY br.bidRequestState ASC");
		model.addAttribute("pageResult",bidRequestService.queryPage(qo));
		return "invest_list";
	}
}
