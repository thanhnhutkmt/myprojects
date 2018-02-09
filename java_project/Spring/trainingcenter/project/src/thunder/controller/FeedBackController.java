/**
 * 
 */
package thunder.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import thunder.bean.RegCourses;
import thunder.bean.ResultClass;
import thunder.entity.Classes;
import thunder.entity.Courses;
import thunder.entity.Feedbacks;
import thunder.entity.Message;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("/")
public class FeedBackController {
	@Autowired
	SessionFactory factory;	
	
	@RequestMapping("lienhe")
	public String lienhe(ModelMap model) {
//		C1
	    String sql = "select cl.openDate, cl.timeTable, cl.room, c.title, c.description "
	    		+ "from Classes cl join cl.course c "
	    		+ "where cl.courseid = 1";
	    List<Object> li = factory.openSession().createQuery(sql).list();
	    List<ResultClass> lr = new ArrayList();	    
	    for (Object o : li) lr.add(ResultClass.objectToThis(o));
	    try {
			System.out.println(new ObjectMapper().writeValueAsString(lr));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
	    		
//		C2
//		List<Object> li = factory.openSession().createSQLQuery(
//			"select cl.openDate, cl.timeTable, cl.room, c.title, c.description"
//			+ " from Classes cl, Courses c where cl.courseid = c.courseid and cl.courseid = 1").list();
//	    List<ResultClass> lr = new ArrayList();	    
//	    for (Object o : li) lr.add(ResultClass.objectToThis(o));	    
	    
		model.addAttribute("feedback", new Feedbacks());		
		return "lienhe";
	}
	
	@RequestMapping("feedbacks")
	public String feedbacks(ModelMap model) {
		model.addAttribute("feedback", new Feedbacks());
		model.addAttribute("listfeedback", getFeedBacks());
		return "feedbacks";
	}
	
	public List<Feedbacks> getFeedBacks() {
		return factory.openSession().createQuery("FROM Feedbacks").list();
	}
	
	@RequestMapping("sendfeedback")
	public String sendfeedback(ModelMap model, Feedbacks feedback) {
		feedback.setSubmittedDate(System.currentTimeMillis());
		insert(model, feedback);
		return lienhe(model);
	}
	
	@RequestMapping("insertfeedback")
	public String insert(ModelMap model,
			Feedbacks feedback) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(feedback);
			session.save(feedback);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return feedbacks(model);
	}
	
	@RequestMapping("updatefeedback")
	public String update(ModelMap model,
			Feedbacks feedback) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(feedback);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại");
		} finally {
			session.close();
		}
		return feedbacks(model);
	}
	
	@RequestMapping("deletefeedback")
	public String delete(ModelMap model,
			@RequestParam("feedbackID")Integer id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Feedbacks p = new Feedbacks(); p.setFeedbackID(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		} 
		return feedbacks(model);
	}
	
	@RequestMapping("editfeedback")
	public String edit(ModelMap model,
			@RequestParam("id")Integer id) {		
		model.addAttribute("feedback", factory.openSession().get(Feedbacks.class, id));
		model.addAttribute("listfeedback", getFeedBacks());
		return "feedbacks";
	}
	
	@RequestMapping("sendmessage")
	public String sendmessage(ModelMap model) {
		model.addAttribute("msg", new Message());		
		return "sendmsg";
	}
	
	@RequestMapping("insertmessage")
	public String insert(ModelMap model, Message msg) { 
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		msg.setCreateDate(System.currentTimeMillis());
		msg.setSendDate(System.currentTimeMillis());
		try {
			System.out.println(msg);
			session.save(msg);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return sendmessage(model);
	}
}
