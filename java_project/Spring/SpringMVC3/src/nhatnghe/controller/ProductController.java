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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eshop.entity.Category;
import eshop.entity.EShopV10;
import eshop.entity.Product;

/**
 * @author Nhut Luu
 *
 */
@Controller
@RequestMapping("product")
public class ProductController {
	@RequestMapping("index")
	public String index(ModelMap model) {
		model.addAttribute("model", new Product());
		model.addAttribute("prods", getProducts());
		return "product";
	}

	@RequestMapping("edit")
	public String edit(ModelMap model, @RequestParam("id")Integer id) {
		Session session = EShopV10.openSession();
		Product p = (Product) session.get(Product.class, id);
		model.addAttribute("model", p);
		model.addAttribute("prods", getProducts());
		return "product";
	}
	
	@RequestMapping("insert")
	public String insert(ModelMap model, Product product) {
		Session session = EShopV10.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(product);
			t.commit();
			model.addAttribute("message", "Thêm mới thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Thêm mới thất bại");
		}
		model.addAttribute("model", new Product());
		model.addAttribute("prods", getProducts());
		return "product";
	}
	
	@RequestMapping("update")
	public String update(ModelMap model, Product product) {
		Session session = EShopV10.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(product);
			t.commit();
			model.addAttribute("message", "Cập nhật thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Cập nhật thất bại");
		}
		model.addAttribute("model", new Product());
		model.addAttribute("prods", getProducts());
		return "product";
	}
	
	@RequestMapping("delete")
	public String delete(ModelMap model, @RequestParam("id")Integer id) {
		Session session = EShopV10.openSession();
		Transaction t = session.beginTransaction();
		Product p = new Product(); p.setId(id);
		try {
			session.delete(p);
			t.commit();
			model.addAttribute("message", "Xóa thành công");
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			model.addAttribute("message", "Xóa thất bại");
		}
		model.addAttribute("model", new Product());
		model.addAttribute("prods", getProducts());
		return "product";
	}
	
	@ModelAttribute("cates")
	public List<Category> getCategories() {
		Session session = EShopV10.openSession();
		String hql = "FROM Category";
		Query query = session.createQuery(hql);
		List<Category> list = query.list();
		return list;
	}
	
	public List<Product> getProducts() {
		Session session = EShopV10.openSession();
		String hql = "FROM Product";
		Query query = session.createQuery(hql);
		List<Product> list = query.list();
		return list;
	}
}
