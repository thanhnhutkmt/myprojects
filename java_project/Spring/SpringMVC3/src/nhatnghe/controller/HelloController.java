package nhatnghe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	@RequestMapping("/say-hello")
	public String sayHello(ModelMap model) {
		model.addAttribute("message", "Chào thế giới Spring MVC");
		return "hello";
	}
}