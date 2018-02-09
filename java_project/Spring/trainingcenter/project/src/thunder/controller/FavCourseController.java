/**
 * 
 */
package thunder.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import thunder.bean.ResultClass;
import thunder.entity.Courses;
import thunder.entity.Favcourses;
import thunder.entity.Learners;
import thunder.util.converter;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("/")
public class FavCourseController {
	@Autowired
	SessionFactory factory;		
	
	@RequestMapping("favcourses")
	public String Favcourses(ModelMap model) {
		model.addAttribute("favcourse", new Favcourses());
		model.addAttribute("listfavcourse", getFavcourses());		
		return "favcourses";
	}
	
	public List<Favcourses> getFavcourses() {
		return factory.openSession().createQuery("FROM Favcourses").list();
	}
	
	@RequestMapping("insertfavcourse")
	public String insert(ModelMap model,
			Favcourses favcourse) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(favcourse);
			session.save(favcourse);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			System.out.println("Rolled back");
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return Favcourses(model);
	}
	
	@RequestMapping("updateFavcourses")
	public String update(ModelMap model,
			Favcourses favcourse) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(favcourse);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại");
		} finally {
			session.close();
		}
		return Favcourses(model);
	}
	
	@RequestMapping("deleteFavcourses")
	public String delete(ModelMap model,
			@RequestParam("favid")Integer id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Favcourses p = new Favcourses(); p.setFavid(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		}
		return Favcourses(model);
	}
	
	@RequestMapping("editFavcourses")
	public String edit(ModelMap model,
			@RequestParam("id")Integer id) {		
		model.addAttribute("favcourse", factory.openSession().get(Favcourses.class, id));
		model.addAttribute("listfavcourse", getFavcourses());
		return "favcourses";
	}
	
	@ResponseBody
	@RequestMapping("registerfavcourse")
	public String registerfavcourse(ModelMap model, HttpServletRequest request, Courses course) {
		int LID = ((Learners) factory.openSession().createQuery("FROM Learners WHERE iDcard='" 
			+ converter.getUsernameFromCookie(request) + "'").list().get(0)).getLearnerId();
		int CID = ((Courses)factory.openSession().createQuery("FROM Courses Where description = '"
			+ course.getDescription() + "'").list().get(0)).getCourseid();
		insert(model, new Favcourses(CID, LID));	
		return "{\"ketqua\":\"" + ((model.get("message").equals("Thêm thành công")) ? 
			"Đã thêm vào danh sách yêu thích" : "Bị lỗi không thêm vào được") + "\"}";		
	}
	//old version
//	public String registerfavcourse(ModelMap model, HttpServletRequest request, Courses course) {		
//		System.out.println(course);
//		String username = converter.getUsernameFromCookie(request);
//		List<Learners> ll = factory.openSession().createQuery(
//			"FROM Learners WHERE iDcard='" + username + "'").list();
////		List<Courses> lc = factory.openSession().createQuery(								
////			"FROM Courses WHERE description = '" + course.getDescription() + "'").list();
//		List<Courses> list1 = factory.openSession().createQuery("FROM Courses").list();
//		for (int i = 0; i < list1.size(); i++) {
//			if (list1.get(i).getDescription().equals(course.getDescription())) {
//				course.setCourseid(list1.get(i).getCourseid());
//				break;
//			}
//		}		
//		Favcourses fc = new Favcourses();
//		fc.setCourseid(course.getCourseid());
//		fc.setLearnerid(ll.get(0).getLearnerId());
//		insert(model, fc);	
//		String ketqua = (model.get("message").equals("Thêm thành công")) ? 
//				"Đã thêm vào danh sách yêu thích" : "Bị lỗi không thêm vào được";
//		return "{\"ketqua\":\"" + ketqua + "\"}";
//	}	
	
	@ResponseBody
	@RequestMapping("removefavcourse")
	public String removefavcourse(ModelMap model, HttpServletRequest request, Courses course) {		
		int LID = ((Learners) factory.openSession().createQuery("FROM Learners WHERE iDcard='" 
			+ converter.getUsernameFromCookie(request) + "'").list().get(0)).getLearnerId();
		int CID = ((Courses)factory.openSession().createQuery("FROM Courses Where description = '"
			+ course.getDescription() + "'").list().get(0)).getCourseid();		
		int affectedRow = factory.openSession().createQuery(
			"DELETE FROM Favcourses WHERE courseid=" + CID + " and learnerid=" + LID).executeUpdate();						
		return "listfavcourse";	
	}
	//old version
//	public String removefavcourse(ModelMap model, HttpServletRequest request, Courses course) {		
//		System.out.println(course);
//		String username = converter.getUsernameFromCookie(request);
//		List<Learners> ll = factory.openSession().createQuery(
//			"FROM Learners WHERE iDcard='" + username + "'").list();
////		List<Courses> lc = factory.openSession().createQuery(								
////			"FROM Courses WHERE description = '" + course.getDescription() + "'").list();
//		List<Courses> list1 = factory.openSession().createQuery("FROM Courses").list();
//		for (int i = 0; i < list1.size(); i++) {
//			if (list1.get(i).getDescription().equals(course.getDescription())) {
//				course.setCourseid(list1.get(i).getCourseid());
//				break;
//			}
//		}		
//		int affectedRow = factory.openSession().createQuery(
//				"DELETE FROM Favcourses WHERE courseid=" + course.getCourseid() 
//				+ " and learnerid=" + ll.get(0).getLearnerId()).executeUpdate();
//						
//		return "listfavcourse";//listfavcourse(model, request, course);	
//	}
	
	@RequestMapping("listfavcourse")	
	public String listfavcourse(ModelMap model, HttpServletRequest request, Courses course) {										
	    List<Favcourses> li = factory.openSession().createQuery("FROM Favcourses fc where fc.learners.iDcard='" 
			+ converter.getUsernameFromCookie(request) + "'").list();
	    List<Courses> lc = new ArrayList();	    
	    for (Favcourses fc : li) lc.add(fc.getCourses());		
		model.addAttribute("listfavcourse", lc);
		return "listfavcourse";
	}
	//old version 1
//	public String listfavcourse(ModelMap model, HttpServletRequest request, Courses course) {										
//		String sql = "FROM Favcourses fc join fc.learners l join fc.courses c "
//			+ "where l.iDcard='" + converter.getUsernameFromCookie(request) + "'";
//	    List<Object> li = factory.openSession().createQuery(sql).list();
//	    List<Courses> lc = new ArrayList();	    
//	    for (Object o : li) lc.add((Courses) ((Object[]) o)[2]);		
//		model.addAttribute("listfavcourse", lc);
//		return "listfavcourse";
//	}
	//old version
//	public String listfavcourse(ModelMap model, HttpServletRequest request, Courses course) {		
//		System.out.println(course);
//		String username = converter.getUsernameFromCookie(request);
//		List<Learners> ll = factory.openSession().createQuery(
//			"FROM Learners WHERE iDcard='" + username + "'").list();
//		List<Favcourses> lfc = factory.openSession().createQuery(								
//			"FROM Favcourses WHERE learnerid=" + ll.get(0).getLearnerId()).list();
//		List<Courses> lc = new ArrayList<>();
//		for (int i = 0; i<lfc.size(); i++)
//			lc.addAll(factory.openSession().createQuery(								
//				"FROM Courses WHERE courseid=" + lfc.get(i).getCourseid()).list());
//		model.addAttribute("listfavcourse", lc);
//		return "listfavcourse";
//	}
}
