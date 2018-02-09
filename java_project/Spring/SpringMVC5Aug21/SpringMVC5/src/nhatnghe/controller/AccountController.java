package nhatnghe.controller;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eshop.entity.Customer;

@Transactional
@Controller
@RequestMapping("account")
public class AccountController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("register")
	public String register(ModelMap model) {
		Customer user = new Customer();
		model.addAttribute("user", user);
		return "account/register";
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String register(ModelMap model, 
			@ModelAttribute("user") Customer user) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(user);
			t.commit();
			model.addAttribute("message", "Đăng ký thành công!");
		} 
		catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Đăng ký thất bại!");
		}
		finally{
			session.close();
		}
		return "account/register";
	}
	
	@RequestMapping("login")
	public String login() {
		return "account/login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(ModelMap model, 
			@RequestParam("id") String id,
			@RequestParam("password") String pw,
			HttpSession httpSession) {
		Session session = factory.getCurrentSession();
		try {
			Customer user = (Customer) session.get(Customer.class, id);
			if(pw.equals(user.getPassword())){
				model.addAttribute("message", "Đăng nhập thành công !");
				httpSession.setAttribute("user", user);
				String uri = (String) httpSession.getAttribute("uri");
				if(uri != null){
					return "redirect:"+uri;
				}
			}
			else{
				model.addAttribute("message", "Sai mật khẩu !");
			}
		} 
		catch (Exception e) {
			model.addAttribute("message", "Sai tên đăng nhập !");
		}
		
		return "account/login";
	}
	
	@RequestMapping("change-password")
	public String change() {
		return "account/change";
	}
	
	@RequestMapping("logoff")
	public String logoff() {
		return "redirect:/home/index.php";
	}
}
