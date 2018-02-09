/**
 * 
 */
package nhatnghe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nhatnghe.bean.UserInfo;

/**
 * @author Nhut Luu
 *
 */
@Controller
//@RequestMapping("/account/")
@RequestMapping("account")
public class AccountController {
//	@RequestMapping("/account/login")  // không có value=
	@RequestMapping(value="login")
	public String login() {
		//redirect -> views/account/login.jsp
		return "account/login"; 
	}	
	@RequestMapping(value="login/{id}", method=RequestMethod.POST)
	public String login1(ModelMap model,
			@PathVariable("id") int id) {
		//redirect -> views/account/login.jsp
		model.addAttribute("message", "POST");
		return "account/login"; 
	}
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login1a(ModelMap model) {
		//redirect -> views/account/login.jsp
		model.addAttribute("message", "GET");
		return "account/login"; 
	}
	@RequestMapping(value="login", method=RequestMethod.POST, params="cach1")
	public String login2(ModelMap model, HttpServletRequest req) {
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		//redirect -> views/account/login.jsp
		model.addAttribute("message", "POST: " + id + "," + password);
		return "account/login"; 
	}	
	@RequestMapping(value="login", method=RequestMethod.POST, params="cach2")
	public String login3(ModelMap model, 
			@RequestParam("id") String id,
			@RequestParam("password") String password) {
		//redirect -> views/account/login.jsp
		model.addAttribute("message", "POST: " + id + "," + password);
		return "account/login"; 
	}	
	@RequestMapping(value="login", method=RequestMethod.POST, params="cach3")
	public String login4(ModelMap model, UserInfo user) {
		//redirect -> views/account/login.jsp
		model.addAttribute("message", "POST: " + user.getId() + "," + user.getPassword());
		return "account/login"; 
	}	
	@RequestMapping(value="login", method=RequestMethod.POST, params="cach4")
	public String login4(ModelMap model, HttpServletRequest req) {
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		//redirect -> views/account/login.jsp
		model.addAttribute("message", "POST: " + id + "," + password);
		return "account/login"; 
	}	
//	@RequestMapping(value="/account/register") // có value=
	@RequestMapping(value="register")
	public String register() {
		//redirect -> views/account/register.jsp
		return "account/register";
	}	
}
