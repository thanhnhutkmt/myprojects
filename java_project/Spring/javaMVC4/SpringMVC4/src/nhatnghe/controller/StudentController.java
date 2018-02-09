package nhatnghe.controller;

import nhatnghe.bean.Student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("student")
public class StudentController {
	@RequestMapping("index")
	public String index(ModelMap model) {
		model.addAttribute("stu", new Student());
		return "student";
	}
	@RequestMapping("index2")
	public String index2(ModelMap model) {
		model.addAttribute("stu", new Student());
		return "student-client";
	}
	@RequestMapping("index3")
	public String index3(ModelMap model) {
		model.addAttribute("stu", new Student());
		return "student-client-server";
	}
	
	@RequestMapping("insert")
	public String insert(ModelMap model, 
			@ModelAttribute("stu") @Validated Student student, BindingResult errors) {
		if(errors.hasErrors()){
			model.addAttribute("message", "Vui lòng sửa các lỗi sau");
		}
		else{
			model.addAttribute("message", "Chúc mừng bạn đã nhập đúng");
		}
		return "student";
	}
}
