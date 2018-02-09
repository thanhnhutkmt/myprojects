/**
 * 
 */
package nhatnghe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import nhatnghe.bean.Clazz;
import nhatnghe.bean.SinhVien;

/**
 * @author Nhut Luu
 *
 */
@Controller
@RequestMapping("sinh-vien")
public class SinhVienController {
	@RequestMapping("index")
	public String index(ModelMap model) {
		SinhVien sv = new SinhVien();
		sv.setId("TeoNV");
		sv.setFullname("Nguyen Van Teo");
		sv.setMarks(10.0);
		sv.setGender(SinhVien.Male);
		sv.setClazz("PT003");
							
		model.addAttribute("student", sv);
		return "sinh-vien";
	}
	
	@ModelAttribute("clazzs")
	public List<Clazz> getClazzs() {
		List<Clazz> list = new ArrayList<Clazz>();
		list.add(new Clazz("PT001", "Ứng dụng phần mềm"));
		list.add(new Clazz("PT002", "Thiết kế web"));
		list.add(new Clazz("PT003", "Lập trình mobile"));
		list.add(new Clazz("PT004", "Thiết kế đồ họa"));
		return list;
	}
}
