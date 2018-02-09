/**
 * 
 */
package nhatnghe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nhut Luu
 *
 */
@Controller
@RequestMapping("order")
public class OrderController {
	@RequestMapping("checkout")
	public String checkout() {
		return "order/checkout";		
	}
	@RequestMapping("list")
	public String list() {
		return "order/list";		
	}
	@RequestMapping("detail")
	public String detail() {
		return "order/detail";		
	}	
}
