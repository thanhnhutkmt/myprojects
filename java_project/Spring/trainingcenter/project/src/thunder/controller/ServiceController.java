/**
 * 
 */
package thunder.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import thunder.bean.RegCourses;
import thunder.bean.ShowCourses;
import thunder.entity.Classes;
import thunder.entity.Courses;
import thunder.entity.Examination;
import thunder.entity.Favcourses;
import thunder.entity.Learners;
import thunder.entity.Message;
import thunder.entity.Registries;
import thunder.entity.Users;
import thunder.util.converter;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("/")
public class ServiceController {
	@Autowired
	SessionFactory factory;	
	
	@ResponseBody
	@RequestMapping(value = "servicelogin", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String servicelogin(Users user) {
		System.out.println(user);
		List<Users> listuser = factory.openSession().createQuery("FROM Users").list();
		System.out.println("listuser size " + listuser.size());
		for (int i = 0; i < listuser.size(); i++) {			
			Users u = listuser.get(i);
			System.out.println(u);
			if (u.getUserid().equals(user.getUserid()) 
					&& u.getPwd().equals(user.getPwd())) {
				return "{\"result\":\"succeed\"}";				
			}
		}
		return "{\"result\":\"fail\"}";
	}	
	
	/*
 		{
	 		"result":"succeed/fail",
	 		"fullname":"a b c",
	 		"sex":"0/1",
	 		"birthday":"20/03/1989",
	 		"address":"12 dfsdf","mobilephone":"34234324",
	 		"email":"sdfdsf@gmail.com","CMND":"324234",
	 		"regcourse":[ 
	 						{"title":"", "timetable":"", "openday":"", "room":"", "fee":"", "feestatus":""},
							{"title":"", "timetable":"320", "openday":"", "room":"", "fee":"", "feestatus":""}
	 					],
	 		"favcourse":[
	 						{"title":"", "time":"", "fee":"", "group":"", "description":""},
							{"title":"", "time":"", "fee":"", "group":"", "description":""}
	 					]
 		}
	 */	
	@ResponseBody
	@RequestMapping(value = "serviceuserinfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String serviceuserinfo(Users user) {
		System.out.println(user);
		List<Learners> listlearner = factory.openSession().createQuery("FROM Learners").list();
		for (int i = 0; i < listlearner.size(); i++) {
			Learners l = listlearner.get(i);
			if (l.getiDcard().equals(user.getUserid())) {
				return String.format(
					"{\"result\":\"succeed\",\"learnerId\":\"%d\",\"fullname\":\"%s\",\"sex\":\"%s\",\"birthday\":\"%s\"," + 
					"\"address\":\"%s\",\"mobilephone\":\"%s\", \"email\":\"%s\","	+ 
					"\"CMND\":\"%s\",\"image\":\"%s\", \"regcourse\":[%s], \"favcourse\":[%s]}", 
					l.getLearnerId(), l.getLastName() + " " + l.getMiddleName() + " " + l.getFirstName(),
					l.getSexDisplay(), l.getbirthDateString(), l.getAddress(), l.getMobile(), 
					l.getEmail1(), l.getiDcard(), l.getImage(),getListRegCourse(user.getUserid()),
					getListFavCourse(user.getUserid()));
			}
		}
		return "{\"result\":\"fail\"}";
	}	
	
	private String getListRegCourse(String username) {		
		List<Registries> lr = factory.openSession().createQuery(
			"FROM Registries r WHERE r.learners.iDcard='" + username + "'").list();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lr.size(); i++) {			
			Courses c = lr.get(i).getClasses().getCourse();
			Classes cl = lr.get(i).getClasses();
			Registries r = lr.get(i);			
			sb.append(String.format("{\"title\":\"%s\", \"timetable\":\"%s\", \"openday\":\"%s\", "
				+ "\"room\":\"%s\", \"fee\":\"%s\", \"feestatus\":\"%s\"},", 
				c.getTitle(), cl.getTimeTable(), cl.getDisplayValue(), 
				cl.getRoom(), c.getFee(), r.getFeeStatusDisplay() 
			));
		}
		String temp = sb.toString();
		return (temp.length() > 0) ? temp.substring(0, temp.length() - 1) : "";
	}	
//	old version 1
//	private String getListRegCourse(String username) {		
//		List<Object> li = factory.openSession().createQuery("FROM Registries r join r.learners l "
//			+ "join r.classes cl join cl.course WHERE iDcard='" + username + "'").list();		
//		List<RegCourses> lrc = new ArrayList();	    
//		for (int i = 0; i < li.size(); i++) {
//			RegCourses rc = new RegCourses();
//			Courses c = (Courses) ((Object[])li.get(i))[3];
//			Classes cl = (Classes) ((Object[])li.get(i))[2];
//			Registries r = (Registries) ((Object[])li.get(i))[0];
//
//			rc.setTitle(c.getTitle());
//			rc.setTimeTable(cl.getTimeTable());
//			rc.setRoom(cl.getRoom());
//			rc.setOpenDate(cl.getDisplayValue());
//			rc.setDeposit(r.getDeposit());
//			rc.setFeeStatus(r.getFeeStatusDisplay());
//			rc.setFee(c.getFee());	
//			lrc.add(rc);
//		}
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < lrc.size(); i++) {
//			sb.append(String.format("{\"title\":\"%s\", \"timetable\":\"%s\", \"openday\":\"%s\", "
//					+ "\"room\":\"%s\", \"fee\":\"%s\", \"feestatus\":\"%s\"},", 
//					lrc.get(i).getTitle(), lrc.get(i).getTimeTable(), lrc.get(i).getOpenDate(), 
//					lrc.get(i).getRoom(), lrc.get(i).getFee(), lrc.get(i).getFeeStatus() 
//				));
//		}
//		String temp = sb.toString();
//		return (temp.length() > 0) ? temp.substring(0, temp.length() - 1) : "";
//	}	
//	old version
//	private String getListRegCourse(String username) {		
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
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < lrc.size(); i++) {
//			sb.append(String.format("{\"title\":\"%s\", \"timetable\":\"%s\", \"openday\":\"%s\", "
//					+ "\"room\":\"%s\", \"fee\":\"%s\", \"feestatus\":\"%s\"},", 
//					lrc.get(i).getTitle(), lrc.get(i).getTimeTable(), lrc.get(i).getOpenDate(), 
//					lrc.get(i).getRoom(), lrc.get(i).getFee(), lrc.get(i).getFeeStatus() 
//				));
//		}
//		String temp = sb.toString();
//		return (temp.length() > 0) ? temp.substring(0, temp.length() - 1) : "";
//	}
	
	private String getListFavCourse(String username) {		
		String sql = "FROM Favcourses fc where fc.learners.iDcard='" + username + "'";
	    List<Favcourses> lfc = factory.openSession().createQuery(sql).list();	    
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lfc.size(); i++) {
			Courses c = lfc.get(i).getCourses();
			sb.append(String.format("{\"title\":\"%s\", \"time\":\"%s\", \"fee\":\"%s\", "
				+ "\"group\":\"%s\", \"description\":\"%s\"},", c.getTitle(), 
				c.getDuration(), c.getFee(), c.getGroupOfCourse(), c.getDescription() 
			));
		}
		String temp = sb.toString();
		return (temp.length() > 0) ? temp.substring(0, temp.length() - 1) : "";
	}
//	old version 1
//	private String getListFavCourse(String username) {		
//		String sql = "FROM Favcourses fc join fc.learners l join fc.courses c "
//				+ "where l.iDcard='" + username + "'";
//	    List<Object> li = factory.openSession().createQuery(sql).list();
//	    List<Courses> lc = new ArrayList();	    
//	    for (Object o : li) lc.add((Courses) ((Object[]) o)[2]);	
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < lc.size(); i++) {
//			sb.append(String.format("{\"title\":\"%s\", \"time\":\"%s\", \"fee\":\"%s\", "
//					+ "\"group\":\"%s\", \"description\":\"%s\"},", 
//					lc.get(i).getTitle(), lc.get(i).getDuration(), lc.get(i).getFee(), 
//					lc.get(i).getGroupOfCourse(), lc.get(i).getDescription() 
//				));
//		}
//		String temp = sb.toString();
//		return (temp.length() > 0) ? temp.substring(0, temp.length() - 1) : "";
//	}		
//	old version
//	private String getListFavCourse(String username) {		
//		List<Learners> ll = factory.openSession().createQuery(
//			"FROM Learners WHERE iDcard='" + username + "'").list();
//		List<Favcourses> lfc = factory.openSession().createQuery(								
//			"FROM Favcourses WHERE learnerid=" + ll.get(0).getLearnerId()).list();
//		List<Courses> lc = new ArrayList<>();
//		for (int i = 0; i<lfc.size(); i++)
//			lc.addAll(factory.openSession().createQuery(								
//				"FROM Courses WHERE courseid=" + lfc.get(i).getCourseid()).list());
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < lc.size(); i++) {
//			sb.append(String.format("{\"title\":\"%s\", \"time\":\"%s\", \"fee\":\"%s\", "
//					+ "\"group\":\"%s\", \"description\":\"%s\"},", 
//					lc.get(i).getTitle(), lc.get(i).getDuration(), lc.get(i).getFee(), 
//					lc.get(i).getGroupOfCourse(), lc.get(i).getDescription() 
//				));
//		}
//		String temp = sb.toString();
//		return (temp.length() > 0) ? temp.substring(0, temp.length() - 1) : "";
//	}
	
	/*
 		"course":[
			{"title":"", "description":"", "class":[
					{"timetable":"", "room":"", "openday":"", "fee":""}, 
					{"timetable":"", "room":"", "openday":"", "fee":""}
				]
			},
			{"title":"", "description":"", "class":[
					{"timetable":"", "room":"", "openday":"", "fee":""}, 
					{"timetable":"", "room":"", "openday":"", "fee":""}
				]
			}	
		]
	 */
	@ResponseBody
	@RequestMapping(value = "servicelistcourse", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String servicelistcourse() {
		List<Courses> list = factory.openSession().createQuery("FROM Courses").list();
		StringBuilder sb = new StringBuilder();		
		for (int i = 0; i < list.size(); i++) {			
			List<Classes> listclass = (List<Classes>) list.get(i).getClasses();
			StringBuilder sbclass = new StringBuilder();
			for (int j = 0; j < listclass.size(); j++) {
				sbclass.append(String.format("{\"timetable\":\"%s\", \"room\":\"%s\", \"openday\":\"%s\", \"fee\":\"%s\"},", 
					listclass.get(j).getTimeTable(), listclass.get(j).getRoom(), listclass.get(j).getDisplayValue(), list.get(i).getFee()));
			}
			String classstring = sbclass.toString();			
			classstring = (classstring.length() == 0) ? "" : classstring.substring(0, classstring.length() - 1);
			sb.append(String.format("{\"title\":\"%s\", \"description\":\"%s\", \"class\":[%s]},",					
				list.get(i).getTitle(), list.get(i).getDescription(), classstring				
			));
		}		
		String temp = sb.toString();
		temp = (temp.length() == 0) ? "" : temp.substring(0,  temp.length() - 1);		
		return String.format("{\"course\":[%s]}", temp);
	}	
	
	@ResponseBody
	@RequestMapping(value = "serviceregisterclasses", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String serviceregisterclasses(ModelMap model, Courses course) {			
		String username = course.getTitle();
		int LID = ((List<Learners>)factory.openSession().createQuery(								
				"FROM Learners WHERE iDcard = '" + username + "'").list()).get(0).getLearnerId();
		List<Classes> list = factory.openSession().createQuery(								
			"FROM Classes WHERE courseid = " + course.getCourseid()).list();
		Classes c = list.get((int) course.getCreatedDate());		

		long ct = System.currentTimeMillis();		
		Registries r = new Registries();
		r.setClassID(c.getClassid()); r.setFeeStatus(false);
		r.setLearnerID(LID); r.setRegisteredDate(ct); r.setDeposit("");
		insert(r);
		List<Registries> lr = factory.openSession().createQuery(								
			String.format("FROM Registries WHERE classID=%d and learnerID=%d and registeredDate=%d", 
			c.getClassid(), LID, ct)).list();
		String data = String.format("Bạn hãy chuyển tiền vào tài khoản "
			+ "Techcombank Thủ Đức chi nhánh Gia Định mang tên Lưu Thành Nhựt số 19026 3377 430 13. "
			+ "Ghi rõ nội dung chuyển tiền \"%s ghi danh mã số %s \"", username, lr.get(0).getRegistryID());
		System.out.println("insert result " + model.get("message"));
		return data;
	}
	
	//old version
//	public String serviceregisterclasses(ModelMap model, Courses course) {
//		String username = course.getTitle();
//		List<Courses> list1 = factory.openSession().createQuery("FROM Courses").list();
//		for (int i = 0; i < list1.size(); i++) {
//			if (list1.get(i).getDescription().equals(course.getDescription())) {
//				course.setCourseid(list1.get(i).getCourseid());
//				break;
//			}
//		}		
//		List<Classes> list = factory.openSession().createQuery(								
//				"FROM Classes WHERE courseid = " + course.getCourseid()).list();
//		List<Learners> listlearner = factory.openSession().createQuery(								
//				"FROM Learners WHERE iDcard = '" + username + "'").list();
//		Classes c = list.get((int) course.getCreatedDate());		
//
//		long ct = System.currentTimeMillis();		
//		Registries r = new Registries();
//		r.setClassID(c.getClassid());
//		r.setFeeStatus(false);
//		r.setLearnerID(listlearner.get(0).getLearnerId());
//		r.setRegisteredDate(ct);
//		r.setDeposit("");
//		insert(r);
//		List<Registries> lr = factory.openSession().createQuery(								
//			String.format("FROM Registries WHERE classID=%d and learnerID=%d and registeredDate=%d", 
//			c.getClassid(), listlearner.get(0).getLearnerId(), ct)).list();
//		String data = String.format(
//			"Bạn hãy chuyển tiền vào tài khoản "
//			+ "Techcombank Thủ Đức chi nhánh Gia Định "
//			+ "mang tên Lưu Thành Nhựt số 19026 3377 430 13. "
//			+ "Ghi rõ nội dung chuyển tiền \"%s ghi danh mã số %s \""
//			, username, lr.get(0).getRegistryID());
//		System.out.println("insert result " + model.get("message"));
//		return data;
//	}
	
	public void insert(Registries registry) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(registry);
			t.commit();			
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();			
		} finally {
			session.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("serviceupdatelearner")
	public String serviceupdatelearner(
			Learners learner, Users user) {	
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(learner);
			if (learner.getiDpassport() == null || learner.getiDpassport().trim().length() == 0) learner.setiDpassport(null);
			session.update(learner);
			t.commit();			
			System.out.println("update learner ok");
		} catch(Exception e) {
			e.printStackTrace();
			t.rollback();
			System.out.println("update learner error");
		} finally {
			session.close();
		}
		return "";		
	}
	
	/*
	 {"examresult":[
	 	{"coursename":"","mark":"","examdate":"","openday":"","schedule":""},
	 	{"coursename":"","mark":"","examdate":"","openday":"","schedule":""} 
	 ]}
 	 */
	@ResponseBody
	@RequestMapping("serviceexamresult")
	public String serviceExamResult(Learners learner) {	
		List<Examination> listexam = factory.openSession().createQuery(
			"FROM Examination Where learnerID = '" + learner.getLearnerId() + "'").list();	

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < listexam.size(); i++) {
			Classes cl = listexam.get(i).getClasses();
			Courses c = listexam.get(i).getClasses().getCourse();
			sb.append(String.format("{\"coursename\":\"%s\",\"mark\":\"%s\",\"examdate\":\"%s\","
				+ "\"openday\":\"%s\",\"schedule\":\"%s\"},", c.getTitle(), listexam.get(i).getMark(), 
				listexam.get(i).getDisplayValue(), cl.getDisplayValue(), cl.getTimeTable()				
			));
		}
		String result = sb.toString();
		result = (result.length() == 0) ? "" : "{\"examresult\":[" + result.substring(0, result.length() - 1) + "]}";
		return result;		
	}
//	old version
//	public String serviceExamResult(Learners learner) {	
//		List<Examination> listexam = factory.openSession().createQuery("FROM Examination").list();
//		List<Examination> listexamoflearner = new ArrayList<Examination>();
//		for (int i = 0; i < listexam.size(); i++) {
//			if (learner.getLearnerId() == listexam.get(i).getLearnerID()) {
//				listexamoflearner.add(listexam.get(i));
//			}
//		}
//		List<Classes> listclass = factory.openSession().createQuery("FROM Classes").list();
//		List<Classes> listclassoflearner = new ArrayList<Classes>();
//		for (int i = 0; i < listclass.size(); i++) {
//			for (int j = 0; j < listexamoflearner.size(); j++) {
//				if (listexamoflearner.get(j).getClassID() == listclass.get(i).getClassid()) { 
//					listclassoflearner.add(listclass.get(i));
//				}
//			}
//		}
//		List<Courses> listcourse = factory.openSession().createQuery("FROM Courses").list();
//		List<Courses> listcourseoflearner = new ArrayList<Courses>();
//		for (int i = 0; i < listcourse.size(); i++) {
//			for (int j = 0; j < listclassoflearner.size(); j++) {
//				if (listclassoflearner.get(j).getCourseid() == listcourse.get(i).getCourseid()) { 
//					listcourseoflearner.add(listcourse.get(i));
//				}
//			}
//		}
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < listexamoflearner.size(); i++) {
//			sb.append(String.format("{\"coursename\":\"%s\",\"mark\":\"%s\",\"examdate\":\"%s\","
//				+ "\"openday\":\"%s\",\"schedule\":\"%s\"},", listcourseoflearner.get(i).getTitle(),
//				listexamoflearner.get(i).getMark(), listexamoflearner.get(i).getDisplayValue(),
//				listclassoflearner.get(i).getDisplayValue(), listclassoflearner.get(i).getTimeTable()				
//				));
//		}
//		String result = sb.toString();
//		result = (result.length() == 0) ? "" : "{\"examresult\":[" + result.substring(0, result.length() - 1) + "]}";
//		return result;		
//	}
	
	@ResponseBody
	@RequestMapping(value = "servicemessage", produces = "text/plain;charset=UTF-8")
	public String servicemessage(Message msg) {		
		List<Message> lm = factory.openSession().createQuery("FROM Message").list();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lm.size(); i++) {
			sb.append(String.format("{\"msg\":\"%s\",\"createday\":\"%s\",\"sendday\":\"%s\"},", 
				lm.get(i).getMsg(), lm.get(i).getDisplayCreateDate(), lm.get(i).getDisplaySendDate()));
		}
		String t = sb.toString();
		t = (t.length() == 0) ? "" : t.substring(0, t.length() - 1);
		return "{\"message\":[" + t + "]}";
	}
}
