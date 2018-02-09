/**
 * 
 */
package nhatnghe.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eshop.entity.Customer;
import eshop.entity.EShopV10;
import eshop.entity.Product;

/**
 * @author Nhut Luu
 *
 */
@Controller
@RequestMapping("customer")
public class CustomerController {
	@RequestMapping("index")
	public String index(ModelMap model) {
		model.addAttribute("list", getCustomerList());
		return "customer";
	}
	
	@RequestMapping("insert")
	public String index(ModelMap model, Customer user) {
		Session session = EShopV10.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(user);
			t.commit();
			model.addAttribute("message", "Insert thành công");
		} catch(Exception e) {			
			t.rollback();
			model.addAttribute("message", "Insert thất bại");
		}
		model.addAttribute("list", getCustomerList());
		return "customer";
	}
	
	@RequestMapping("update")
	public String update(ModelMap model, Customer user) {
		Session session = EShopV10.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(user);
			t.commit();
			model.addAttribute("message", "Update thành công");
		} catch(Exception e) {			
			t.rollback();
			model.addAttribute("message", "Update thất bại");
		}
		model.addAttribute("list", getCustomerList());
		return "customer";
	}
	
	@RequestMapping("delete")
	public String delete(ModelMap model, Customer user) {
		Session session = EShopV10.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(user);
			t.commit();
			model.addAttribute("message", "Delete thành công");
		} catch(Exception e) {			
			t.rollback();
			model.addAttribute("message", "Delete thất bại");
		}
		model.addAttribute("list", getCustomerList());
		return "customer";
	}

	private List<Customer> getCustomerList() {
		String sql = "FROM Customer";
		Session session = EShopV10.openSession();
		Query query = session.createQuery(sql);
		List<Customer> list = query.list();				
		return list;
	}
	
	@RequestMapping("edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") String id) {
		Session session = EShopV10.openSession();
		Customer user = (Customer) session.get(Customer.class, id);
		model.addAttribute("user", user);
		model.addAttribute("list", getCustomerList());
		return "customer";
	}
}
