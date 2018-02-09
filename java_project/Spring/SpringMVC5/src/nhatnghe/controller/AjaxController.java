package nhatnghe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import eshop.entity.Category;

@Controller
@RequestMapping("ajax")
public class AjaxController {
	@RequestMapping("index")
	public String index() {		
		return "ajax/index";
	}
	
	@ResponseBody	
	@RequestMapping("json1")
	public String json1() {		
		String text = "{\"id\":\"NghiemN\", \"age\":45}";
		return text;
	}
	@ResponseBody	
	@RequestMapping("json2")
	public String json2() {		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "NghiemN");
		map.put("age", "45");
		try {
			ObjectMapper mapper = new ObjectMapper();
			String text = mapper.writeValueAsString(map);
			return text;
		} catch(Exception e) {
			return "Lỗi";
		}
	}	
	
	@ResponseBody	
	@RequestMapping("json3")
	public String json3() {		
		Category cate = new Category();
		cate.setId(1000);
		cate.setName("Mobile");
		cate.setNameVN("Điện thoại di động");
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String text = mapper.writeValueAsString(cate);
			return text;
		} catch(Exception e) {
			return "Lỗi";
		}
	}
	
	@ResponseBody	
	@RequestMapping("json4")
	public String json4() {		
		String a[] = {"Tuấn", "Phương", "Cường"};
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String text = mapper.writeValueAsString(a);
			return text;
		} catch(Exception e) {
			return "Lỗi";
		}
	}
	
	@ResponseBody	
	@RequestMapping("json5")
	public String json5() {		
		List<String> list = new ArrayList<String>();
		list.add("Tuan");
		list.add("Cuong");
		list.add("Phuong");
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String text = mapper.writeValueAsString(list);
			return text;
		} catch(Exception e) {
			return "Lỗi";
		}
	}
	
	@ResponseBody	
	@RequestMapping("json6")
	public String json6() {		
		List<Category> list = new ArrayList<Category>();
		Category cate1 = new Category();
		cate1.setId(4000);
		cate1.setName("Mobile");
		cate1.setNameVN("Điện thoại di động");
		Category cate2 = new Category();
		cate2.setId(2000);
		cate2.setName("Laptop");
		cate2.setNameVN("Máy tính xách tay");
		list.add(cate1);
		list.add(cate2);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String text = mapper.writeValueAsString(list);
			return text;
		} catch(Exception e) {
			return "Lỗi";
		}
	}
	
	//@ResponseBody
	@RequestMapping("hello")
	public String hello() {		
//		return "Chào quý vị";
		return "ajax/index";
	}
}	
