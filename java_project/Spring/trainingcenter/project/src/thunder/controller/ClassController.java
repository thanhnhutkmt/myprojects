/**
 * 
 */
package thunder.controller;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

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

import thunder.entity.Classes;
import thunder.entity.Courses;
import thunder.entity.Learners;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("/")
public class ClassController {
	@Autowired
	SessionFactory factory;		
	
	@RequestMapping("classes")
	public String classes(ModelMap model) {
		model.addAttribute("clazz", new Classes());
		model.addAttribute("listclass", getClasses());		
		return "classes";
	}
	
	public List<Classes> getClasses() {
		return factory.openSession().createQuery("FROM Classes").list();
	}
	
	@RequestMapping("insertclasses")
	public String insert(ModelMap model,
			Classes clazz) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(clazz);
			session.save(clazz);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return classes(model);
	}
	
	@RequestMapping("updateclasses")
	public String update(ModelMap model,
			Classes clazz) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(clazz);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại");
		} finally {
			session.close();
		}
		return classes(model);
	}
	
	@RequestMapping("deleteclasses")
	public String delete(ModelMap model,
			@RequestParam("classid")Integer id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Classes p = new Classes(); p.setClassid(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		}
		return classes(model);
	}
	
	@RequestMapping("editclass")
	public String editclass(ModelMap model,
			@RequestParam("id")Integer id) {		
		model.addAttribute("clazz", factory.openSession().get(Classes.class, id));
		model.addAttribute("listclass", getClasses());
		return "classes";
	}		
}