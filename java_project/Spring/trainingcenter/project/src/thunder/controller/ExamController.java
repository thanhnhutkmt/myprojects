/**
 * 
 */
package thunder.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thunder.entity.Examination;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("/")
public class ExamController {
	@Autowired
	SessionFactory factory;		
	
	@RequestMapping("exams")
	public String exams(ModelMap model) {
		model.addAttribute("exam", new Examination());
		model.addAttribute("listexam", getExams());		
		return "exams";
	}
	
	public List<Examination> getExams() {
		return factory.openSession().createQuery("FROM Examination").list();
	}
	
	@RequestMapping("insertexams")
	public String insert(ModelMap model,
			Examination exam) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(exam);
			session.save(exam);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return exams(model);
	}
	
	@RequestMapping("updateexams")
	public String update(ModelMap model,
			Examination exam) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(exam);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại");
		} finally {
			session.close();
		}
		return exams(model);
	}
	
	@RequestMapping("deleteexams")
	public String delete(ModelMap model,
			@RequestParam("examID")Integer id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Examination p = new Examination(); p.setExamID(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		}
		return exams(model);
	}
	
	@RequestMapping("editexam")
	public String edit(ModelMap model,
			@RequestParam("id")Integer id) {		
		model.addAttribute("exam", factory.openSession().get(Examination.class, id));
		model.addAttribute("listexam", getExams());
		return "exams";
	}		
}
