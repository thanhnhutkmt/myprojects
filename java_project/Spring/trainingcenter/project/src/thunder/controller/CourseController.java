/**
 * 
 */
package thunder.controller;

import java.io.File;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import thunder.bean.ResultClass;
import thunder.bean.ShowCourses;
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
public class CourseController {
	@Autowired
	SessionFactory factory;		
	
	@RequestMapping("courses")
	public String courses(ModelMap model) {
		model.addAttribute("course", new Courses());
		model.addAttribute("listcourse", getCourses());		
		return "courses";
	}
	
	public List<Courses> getCourses() {
		Session session = factory.openSession();
		String hql = "FROM Courses";
		Query query = session.createQuery(hql);
		List<Courses> list = query.list();
		return list;
	}
	
	@RequestMapping("insertcourse")
	public String insert(ModelMap model,
			Courses course) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(course);
			session.save(course);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return courses(model);
	}
	
	@RequestMapping("updatecourse")
	public String update(ModelMap model,
			Courses course) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(course);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại");
		} finally {
			session.close();
		}
		return courses(model);
	}
	
	@RequestMapping("deletecourse")
	public String delete(ModelMap model,
			@RequestParam("courseid")Integer id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Courses p = new Courses(); p.setCourseid(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		}
		return courses(model);
	}
	
	@RequestMapping("editcourse")
	public String edit(ModelMap model,
			@RequestParam("id")Integer id) {		
		model.addAttribute("course", factory.openSession().get(Courses.class, id));
		model.addAttribute("listcourse", getCourses());
		return "courses";
	}	
	
	public List<Classes> getClasses() {
		return factory.openSession().createQuery("FROM Classes").list();
	}
	
	@RequestMapping("khoahoc")
	public String khoahoc(ModelMap model) {				
		model.addAttribute("course", new Courses());
		model.addAttribute("listcourse", getCourses());
		return "khoahoc";
	}
	
	@ResponseBody
	@RequestMapping("getclassschedule")
	public String getclassschedule(Courses course) {		    
		try {		
		    String sql = "select cl.timeTable, cl.room, cl.openDate, cl.course.fee "
	    		+ "from Classes cl where cl.course.description = '" + course.getDescription() + "'";
		    List<Object> list = factory.openSession().createQuery(sql).list();
		    List<ShowCourses> lsc = new ArrayList();
		    for (Object o : list) lsc.add(ShowCourses.objectToThis(o));
			return new ObjectMapper().writeValueAsString(lsc);								
		} catch (Exception e) {
			e.printStackTrace(); 
			return "Loi";
		}
	}	
//	old version 1
//	public String getclassschedule(Courses course) {		    
//		try {		
//		    String sql = "select cl.timeTable, cl.room, cl.openDate, c.fee "
//	    		+ "from Classes cl join cl.course c "
//	    		+ "where c.description = '" + course.getDescription() + "'";
//		    List<Object> list = factory.openSession().createQuery(sql).list();
//		    List<ShowCourses> lsc = new ArrayList();
//		    for (Object o : list) lsc.add(ShowCourses.objectToThis(o));
//			return new ObjectMapper().writeValueAsString(lsc);								
//		} catch (Exception e) {
//			e.printStackTrace(); 
//			return "Loi";
//		}
//	}	
	
	// old version
//	public String getclassschedule(Courses course) {		    
//		System.out.println(course);
//		List<Courses> list1 = factory.openSession().createQuery("FROM Courses").list();
//		for (int i = 0; i < list1.size(); i++) {
//			if (list1.get(i).getDescription().equals(course.getDescription())) {
//				course.setCourseid(list1.get(i).getCourseid());
//				break;
//			}
//		}
//		List<Classes> list = factory.openSession().createQuery(								
//			"FROM Classes WHERE courseid = " + course.getCourseid()).list();
//		try {
//			List<ShowCourses> lsc = new ArrayList<>();
//			for (int i = 0; i < list.size(); i++) {
//				ShowCourses sc = new ShowCourses();	
//				sc.setTimeTable(list.get(i).getTimeTable());
//				sc.setRoom(list.get(i).getRoom());
//				sc.setOpenDate(list.get(i).getDisplayValue());
//				sc.setFee(list1.get(0).getFee());
//				lsc.add(sc);
//				System.out.println(sc);
//			}	
//			
//			return new ObjectMapper().writeValueAsString(lsc);			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Loi";
//		}
//	}	
}
