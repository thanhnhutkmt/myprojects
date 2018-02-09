package eshop.entity;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class EShopV10 {
	public static Session openSession() {
		Configuration config = new AnnotationConfiguration()
				.configure("eshop.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		return session;
	}

	public static void main(String[] args) {
		// demo1();
		// demo2();
		demo3();
		//demo4();

	}

	private static void demo4() {
		
		Scanner kb = new Scanner(System.in);

		System.out.print("UserName:");
		String id = kb.nextLine();

		System.out.print("Pass:");
		String pass = kb.nextLine();
		Session session = EShopV10.openSession();
		try {
			Customer user = (Customer) session.get(Customer.class, id);
			if (pass.endsWith(user.getPassword())) {
				System.out.println("Login Seccessfully !");
			} else {
				System.out.println("Invalid Password !");
			}
		} catch (Exception e) {
			System.out.println("Invalid Username!");
		}
	}

	private static void demo3() {
		Customer entity = new Customer();
		entity.setId("hien");
		entity.setPassword("hien");
		entity.setFullname("Nguyen Thanh Hien");
		entity.setEmail("hiennt@gmail.com");
		
		

		Session session = EShopV10.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(entity);
			t.commit();
		} catch (Exception e) {
			t.rollback();
		}

	}

	private static void demo2() {
		Session session = EShopV10.openSession();
		String hql = "FROM Product Where unitPrice BETWEEN :min AND :max";
		Query query = session.createQuery(hql);
		query.setDouble("min", 5.0);
		query.setDouble("max", 10.0);
		List<Product> list = query.list();

		for (Product p : list) {
			System.out.println("Name:" + p.getName());
			System.out.println("UnitPrice" + p.getUnitPrice());
			System.out.println();
		}

	}

	private static void demo1() {
		Session session = EShopV10.openSession();
		// Configuration config = new
		// AnnotationConfiguration().configure("eshop.cfg.xml");
		// SessionFactory factory = config.buildSessionFactory();
		// Session session = factory.openSession();

		

		OrderDetail detail = (OrderDetail) session.get(OrderDetail.class,
				100007);
		Order order = detail.getOrder();
		Customer customer = order.getCustomer();
		Product product = detail.getProduct();
		Category category = product.getCategory();

		System.out.println("OderDetailId: " + detail.getId());
		System.out.println("OderId: " + order.getId());
		System.out.println("ProductId: " + product.getId());
		System.out.println("CustomerId: " + customer.getId());
		System.out.println("CategoryId: " + category.getId());

	}

}
