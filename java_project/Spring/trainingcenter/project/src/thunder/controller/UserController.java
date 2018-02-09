/**
 * 
 */
package thunder.controller;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import thunder.entity.Feedbacks;
import thunder.entity.Users;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("/")
public class UserController {
	@Autowired
	SessionFactory factory;		
	
	@RequestMapping("users")
	public String users(ModelMap model) {
		model.addAttribute("user", new Users());
		model.addAttribute("listuser", getUsers());		
		return "users";
	}
	
	public List<Users> getUsers() {
		return factory.openSession().createQuery("FROM Users").list();
	}
	
	@RequestMapping("insertusers")
	public String insert(ModelMap model,
			Users user) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(user);
			session.save(user);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return users(model);
	}
	
	@RequestMapping("updateusers")
	public String update(ModelMap model,
			Users user) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(user);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại");
		} finally {
			session.close();
		}
		return users(model);
	}
	
	@RequestMapping("deleteusers")
	public String delete(ModelMap model,
			@RequestParam("userid")String id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Users p = new Users(); p.setUserid(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		}
		return users(model);
	}
	
	@RequestMapping("edituser")
	public String edit(ModelMap model,
			@RequestParam("id")String id) {		
		model.addAttribute("user", factory.openSession().get(Users.class, id));
		model.addAttribute("listuser", getUsers());
		return "users";
	}
	
	private boolean checkLogin(String id, String pass) {
		List<Users> l = getUsers();
		for (int i = 0; i < l.size(); i++) 
			if (l.get(i).getUserid().equals(id)) 
				return l.get(i).getPwd().equals(pass);				
		return false;
	}
	
	@RequestMapping("loginresult")
	public String login(ModelMap model, HttpServletResponse response, 
			@RequestParam("usern")String id, 
			@RequestParam("password")String pass) {		
		if (checkLogin(id, pass)) {
			Cookie c = new Cookie("username", id);
			c.setMaxAge(1000*60*60*3);
			response.addCookie(c);			
			model.addAttribute("regresult", "Đăng nhập thành công");
		} else model.addAttribute("regresult", "Đăng nhập thất bại");
		return "loginresult";
	}
	
	@RequestMapping("logout")
	public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse resp) {			
		for (Cookie c : request.getCookies()) 
			if (c.getName().equals("username")) {				
				c.setMaxAge(0); 
				resp.addCookie(c);
				break;
			}	
		model.addAttribute("regresult", "Đăng xuất đã xong. Chào hẹn gặp lại.");	
		return "logout";
	}
}
