/**
 * 
 */
package nhatnghe.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nhut Luu
 *
 */
@Controller
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	ServletContext application;
	
	@RequestMapping("insert")
	public String insert1(ModelMap model, 
			HttpServletRequest req,
			HttpSession session, 
			HttpServletResponse resp,
			@CookieValue(value="uid", defaultValue="123") String uid) {
		session.setAttribute("x", "Tuan");
		application.setAttribute("x", "Chuong");
		
		model.addAttribute("message", "không có tham số a");
		return "customer/insert";
	}
	@RequestMapping(value="insert", params="a")
	public String insert2(ModelMap model) {
		model.addAttribute("message", "Có tham số a");
		return "customer/insert";
	}	
	@RequestMapping(value="insert", params="b")
	public String insert3(ModelMap model) {
		model.addAttribute("message", "Có tham số b");
		return "customer/insert";
	}
	@RequestMapping(value="insert", params="c")
	public String insert4(ModelMap model) {
		model.addAttribute("message", "Có tham số c");
		return "customer/insert";
	}
}