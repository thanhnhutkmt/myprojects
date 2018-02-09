/**
 * 
 */
package thunder.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import thunder.entity.Courses;

/**
 * @author java_dev
 *
 */
@Transactional
@Controller
@RequestMapping("test")
public class CourseController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("index")
	public String index(ModelMap model) {
		model.addAttribute("model", new Courses());
		model.addAttribute("listcourse", getCourses());
		return "test/index";
	}
	
	public List<Courses> getCourses() {
		Session session = factory.openSession();
		String hql = "FROM courses";
		Query query = session.createQuery(hql);
		List<Courses> list = query.list();
		return list;
	}
}
