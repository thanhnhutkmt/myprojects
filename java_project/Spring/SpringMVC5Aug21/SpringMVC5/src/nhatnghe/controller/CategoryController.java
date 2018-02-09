package nhatnghe.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import eshop.entity.Category;

@Transactional
@Controller
@RequestMapping("category")
public class CategoryController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("index")
	public String index() {
		return "category";
	}
	
	@ResponseBody
	@RequestMapping("list")
	public String list() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Category";
		Query query = session.createQuery(hql);
		List<Category> list = query.list();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String text = mapper.writeValueAsString(list);
			
			return text;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "Loi";
		}
	}
	
	@ResponseBody
	@RequestMapping("insert")
	public String insert(Category category) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(category);
			t.commit();
		} 
		catch (Exception e) {
			t.rollback();
			return "Loi";
		}
		finally{
			session.close();
		}
		return "Thanh cong";
	}
}
