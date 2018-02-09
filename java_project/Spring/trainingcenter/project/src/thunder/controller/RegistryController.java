/**
 * 
 */
package thunder.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

import thunder.bean.RegCourses;
import thunder.bean.ResultClass;
import thunder.entity.Classes;
import thunder.entity.Courses;
import thunder.entity.Learners;
import thunder.entity.Registries;
import thunder.util.converter;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("/")
public class RegistryController {
	@Autowired
	SessionFactory factory;		
	
	@RequestMapping("registries")
	public String registries(ModelMap model) {
		model.addAttribute("registry", new Registries());
		model.addAttribute("listregistry", getRegistries());		
		return "registries";
	}
	
	public List<Registries> getRegistries() {
		return factory.openSession().createQuery("FROM Registries").list();
	}
	
	@RequestMapping("insertregistries")
	public String insert(ModelMap model,
			Registries registry) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(registry);
			session.save(registry);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return registries(model);
	}
	
	@RequestMapping("updateregistries")
	public String update(ModelMap model,
			Registries registry) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(registry);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			model.addAttribute("message", "Cập nhật thất bại");
		} finally {
			session.close();
		}
		return registries(model);
	}
	
	@RequestMapping("deleteregistries")
	public String delete(ModelMap model,
			@RequestParam("registryID")Integer id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Registries p = new Registries(); p.setRegistryID(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		}
		return registries(model);
	}
	
	@RequestMapping("editregistry")
	public String edit(ModelMap model,
			@RequestParam("id")Integer id) {		
		model.addAttribute("registry", factory.openSession().get(Registries.class, id));
		model.addAttribute("listregistry", getRegistries());
		return "registries";
	}	
	
	@ResponseBody
	@RequestMapping("registerclasses")
	public String registerclasses(ModelMap model,  
			HttpServletRequest request,	Courses course) {
//		System.out.println(course);
		String username = "";
		for (Cookie c : request.getCookies()) 
			if (c.getName().equals("username")) {				
				username = c.getValue();			
				break;
			}	
		if (username.length() == 0) return "{\"ketqua\":\"Chưa đăng nhập\"}";
//		List<Courses> list1 = factory.openSession().createQuery(								
//				"FROM Courses WHERE description = '" + course.getDescription() + "'").list();
		List<Courses> list1 = factory.openSession().createQuery("FROM Courses").list();
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i).getDescription().equals(course.getDescription())) {
				course.setCourseid(list1.get(i).getCourseid());
				break;
			}
		}		
		List<Classes> list = factory.openSession().createQuery(								
				"FROM Classes WHERE courseid = " + course.getCourseid()).list();
		List<Learners> listlearner = factory.openSession().createQuery(								
				"FROM Learners WHERE iDcard = '" + username + "'").list();
		Classes c = list.get(0);		
//		System.out.println(c);
//		System.out.println(listlearner.get(0));
		long ct = System.currentTimeMillis();		
		Registries r = new Registries();
		r.setClassID(c.getClassid());
		r.setFeeStatus(false);
		r.setLearnerID(listlearner.get(0).getLearnerId());
		r.setRegisteredDate(ct);
		r.setDeposit("");
		insert(model,r);
		List<Registries> lr = factory.openSession().createQuery(								
				String.format("FROM Registries WHERE classID=%d and learnerID=%d and registeredDate=%d", 
					c.getClassid(), listlearner.get(0).getLearnerId(), ct)).list();
		String data = String.format(
			"Bạn hãy chuyển tiền vào tài khoản "
			+ "Techcombank Thủ Đức chi nhánh Gia Định "
			+ "mang tên Lưu Thành Nhựt số 19026 3377 430 13. "
			+ "Ghi rõ nội dung chuyển tiền \"%s ghi danh mã số %s \""
			, username, lr.get(0).getRegistryID());
		System.out.println("insert result " + model.get("message"));
//		return "{\"ketqua\":\"" + data + "\"}" ;
		return data;
	}
	
	@RequestMapping("learnerregisteredcourse")
	public String learnercourseinfo(ModelMap model, HttpServletRequest request) {		
		List<Registries> lr = factory.openSession().createQuery(
			"FROM Registries r WHERE r.learners.iDcard='" 
			+ converter.getUsernameFromCookie(request) + "'").list();						
		List<RegCourses> lrc = new ArrayList();	    
		for (int i = 0; i < lr.size(); i++) {			
			RegCourses rc = new RegCourses();
			Courses c = lr.get(i).getClasses().getCourse();
			Classes cl = lr.get(i).getClasses();
			Registries r = lr.get(i);
			
			//set title			
			rc.setTitle(c.getTitle());
			//set timeTable, room, openDate
			rc.setTimeTable(cl.getTimeTable());
			rc.setRoom(cl.getRoom());
			rc.setOpenDate(cl.getDisplayValue());
			//set deposit, fee, feeStatus
			rc.setDeposit(r.getDeposit());
			rc.setFeeStatus(r.getFeeStatusDisplay());
			rc.setFee(c.getFee());	
			lrc.add(rc);
		}
		model.addAttribute("listregcourse", lrc);
		return "registeredcourse";
	}
	
//	old version 1
//	public String learnercourseinfo(ModelMap model, HttpServletRequest request) {		
//		List<Object> li = factory.openSession().createQuery("FROM Registries r join r.learners l join r.classes cl "
//		+ "join cl.course WHERE iDcard='" + converter.getUsernameFromCookie(request) + "'").list();						
//		List<RegCourses> lrc = new ArrayList();	    
//		for (int i = 0; i < li.size(); i++) {
//			System.out.println(Arrays.toString((Object[])li.get(i)));
//			RegCourses rc = new RegCourses();
//			Courses c = (Courses) ((Object[])li.get(i))[3];
//			Classes cl = (Classes) ((Object[])li.get(i))[2];
//			Registries r = (Registries) ((Object[])li.get(i))[0];
//			
//			//set title			
//			rc.setTitle(c.getTitle());
//			//set timeTable, room, openDate
//			rc.setTimeTable(cl.getTimeTable());
//			rc.setRoom(cl.getRoom());
//			rc.setOpenDate(cl.getDisplayValue());
//			//set deposit, fee, feeStatus
//			rc.setDeposit(r.getDeposit());
//			rc.setFeeStatus(r.getFeeStatusDisplay());
//			rc.setFee(c.getFee());	
//			lrc.add(rc);
//		}
//		model.addAttribute("listregcourse", lrc);
//		return "registeredcourse";
//	}
	//old version
//	public String learnercourseinfo(ModelMap model, HttpServletRequest request) {
////		List<Learners> list = factory.openSession().createQuery("FROM Learners WHERE iDcard='" 
////				+ converter.getUsernameFromCookie(request) + "'").list();
////		System.out.println(list.get(0));
////		List<Registries> lr = factory.openSession().createQuery("FROM Registries WHERE learnerID=" 
////				+ list.get(0).getLearnerId()).list();
////		List<Classes> lcl = new ArrayList<Classes>();
////		for (Registries r : lr) 
////			lcl.addAll(factory.openSession().createQuery("FROM Classes WHERE classid=" 
////				+ r.getClassID()).list());		
////		List<Courses> lc = factory.openSession().createQuery("FROM Courses WHERE learnerID=" 
////				+ list.get(0).getLearnerId()).list();
////		model.addAttribute("listregcourse", lcl);
////		String username = converter.getUsernameFromCookie(request);
////		List<RegCourses> l = factory.openSession().createQuery(
////		"select co.title, b.timeTable, b.room, b.openDate, b.deposit, co.fee, b.feeStatus from select aaa.learnerId, aaa.ClassID, aaa.deposit, aaa.feeStatus, cl.openDate, cl.timeTable, cl.room, cl.courseid from select l.learnerId, r.classID, r.deposit, r.feeStatus from From Learner where iDcard='" + username + "' l inner join Registries r on l.learnerid=r.learnerID aaa inner join Classes cl on aaa.classID=cl.classid b inner join Courses co on co.courseid=b.courseid").list();
//		
//		String username = converter.getUsernameFromCookie(request);
//		List<Learners> ll = factory.openSession().createQuery("FROM Learners WHERE iDcard='" + username + "'").list();
//		System.out.println("ll " + ll.size());
//		List<Registries> lr = factory.openSession().createQuery("FROM Registries WHERE learnerId=" + ll.get(0).getLearnerId()).list();
//		System.out.println("lr " + lr.size());
//		List<Classes> lcl = new ArrayList<Classes>();
//		for (int i = 0; i < lr.size(); i++)
//			lcl.add((Classes) factory.openSession().createQuery("FROM Classes WHERE classid=" + lr.get(i).getClassID()).list().get(0));		
//		System.out.println("lcl " + lcl.size());
//		List<RegCourses> lrc = new ArrayList<>();
//		for (int i = 0; i < lcl.size(); i++) {
//			RegCourses rc = new RegCourses();
//			//set title
//			List<Courses> lc = factory.openSession().createQuery("FROM Courses WHERE courseid=" + lcl.get(i).getCourseid()).list();
//			rc.setTitle(lc.get(0).getTitle());
//			//set timeTable, room, openDate
//			rc.setTimeTable(lcl.get(i).getTimeTable());
//			rc.setRoom(lcl.get(i).getRoom());
//			rc.setOpenDate(lcl.get(i).getDisplayValue());
//			//set deposit, fee, feeStatus
//			rc.setDeposit(lr.get(i).getDeposit());
//			rc.setFeeStatus(lr.get(i).getFeeStatusDisplay());
//			rc.setFee(lc.get(0).getFee());	
//			lrc.add(rc);
//		}
//		
//		System.out.println("lrc " + lrc.size());
//		model.addAttribute("listregcourse", lrc);
//		return "registeredcourse";
//	}
}
