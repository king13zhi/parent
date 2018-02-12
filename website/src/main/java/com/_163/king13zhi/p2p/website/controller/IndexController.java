package com._163.king13zhi.p2p.website.controller;

import com._163.king13zhi.p2p.base.util.BidConst;
import com._163.king13zhi.p2p.business.query.BidRequestQueryObject;
import com._163.king13zhi.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kingdan on 2018/1/27.
 */
@Controller
public class IndexController {
	@Autowired
	private IBidRequestService bidRequestService;

	@RequestMapping("/index")
	public String indexPage(BidRequestQueryObject qo,Model model) {
		//前台数据有限封装
		qo.setCurrentPage(1);
		qo.setPageSize(5);
		qo.setStates(new int[] {
			BidConst.BIDREQUEST_STATE_BIDDING,BidConst.BIDREQUEST_STATE_PAYING_BACK,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
		qo.setOrderByCondition("ORDER BY br.bidRequestState ASC");
		model.addAttribute("bidRequests",bidRequestService.queryIndexList(qo));
		return "main";
	}
}
