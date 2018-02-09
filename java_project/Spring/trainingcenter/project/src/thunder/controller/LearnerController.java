/**
 * 
 */
package thunder.controller;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import thunder.entity.Learners;
import thunder.entity.Users;
import thunder.util.converter;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("/")
public class LearnerController {
	@Autowired
	SessionFactory factory;		
	
	@RequestMapping("learners")
	public String learners(ModelMap model) {
		model.addAttribute("learner", new Learners());
		model.addAttribute("listlearner", getLearners());		
		return "learners";
	}		
	
	public List<Learners> getLearners() {
		return factory.openSession().createQuery("FROM Learners").list(); 
	}
	
	@RequestMapping("insertlearner")
	public String insert(ModelMap model,
			Learners learner) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(learner);
			session.save(learner);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return learners(model);
	}
	
	@RequestMapping("updatelearner")
	public String update(ModelMap model,
			Learners learner) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(learner);
			if (learner.getiDpassport().trim().length() == 0) learner.setiDpassport(null);
			session.update(learner);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại");
		} finally {
			session.close();
		}
		return learners(model);
	}
	
	@RequestMapping("deletelearner")
	public String deletelearner(ModelMap model,
			@RequestParam("learnerId")Integer id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Learners p = new Learners(); p.setLearnerId(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		}
		return learners(model);
	}
	
	@RequestMapping("editlearner")
	public String editlearner(ModelMap model,
			@RequestParam("id")Integer id) {		
		model.addAttribute("learner", factory.openSession().get(Learners.class, id));
		model.addAttribute("listlearner", getLearners());		
		return "learners";
	}
	
	@Autowired
	ServletContext app;
	
	@RequestMapping("upload")
	public String upload(ModelMap model,
			@RequestParam("image") MultipartFile image) {
		String filename = image.getOriginalFilename();
		String path = app.getRealPath("/image/"+filename);
		try {
			image.transferTo(new File(path));						
			model.addAttribute("filename", filename);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}		
		return learners(model);
	}
	
	
	@RequestMapping("registerlearner")
	public void register(ModelMap model, Learners learner) {
		insert(model, learner);		
		Users u = new Users();
		u.setUserid(learner.getiDcard());
		u.setPwd(converter.genRandomString(9));
		u.setRole("Học viên");
		u.setEmail(learner.getEmail1());
		u.setCreatedDate(System.currentTimeMillis());
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(u);
			session.save(u);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		
		String msg = (String) model.get("message");
		if (msg.equals("Thêm thành công")) 
			model.addAttribute("regresult", "Xin chúc mừng.<br>Bạn đã đăng ký thành công<br>"
					+ "Thông tin đăng nhập của bạn: (username là CMND của bạn)<br>Username:" + u.getUserid() + "<br>Password:" + u.getPwd());
		else model.addAttribute("regresult", "Quá trình đăng ký bị lỗi");
	}
	
	@RequestMapping(value = "learnerinfo")
	public String learnerinfo(ModelMap model, HttpServletRequest request) {
		model.addAttribute("learner", 
			factory.openSession().createQuery("FROM Learners WHERE iDcard='" 
				+ converter.getUsernameFromCookie(request) + "'").list().get(0));			
		return "learnerinfo";
	}
	
	@RequestMapping(value = "learnerinfoupdate", method = RequestMethod.POST)
	public String learnerinfoupdate(ModelMap model, Learners learner, HttpServletRequest request) {
		update(model, learner);		
		System.out.println(learner);
		return learnerinfo(model, request);
	}	
}
