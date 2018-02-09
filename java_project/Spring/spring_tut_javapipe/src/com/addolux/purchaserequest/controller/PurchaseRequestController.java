package com.addolux.purchaserequest.controller;

import com.addolux.purchaserequest.service.PurchaseRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;

@Controller
@RequestMapping("/purchase-request")
public class PurchaseRequestController {
	private static final String MY_REQUESTS_VIEW = "myRequests";
	private static final String MY_REQUESTS_MODEL_ATTRIBUTE = "myRequestList";
	@Resource
	private PurchaseRequestService purchaseRequestService;

	@RequestMapping(value = "/myRequests", method = RequestMethod.GET)
	public String getMyRequests(Model model) {
		model.addAttribute(MY_REQUESTS_MODEL_ATTRIBUTE, purchaseRequestService.getAllPurchaseRequests());
		return MY_REQUESTS_VIEW;
	}
}