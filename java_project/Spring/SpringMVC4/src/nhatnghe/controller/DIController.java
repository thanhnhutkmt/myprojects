/**
 * 
 */
package nhatnghe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import nhatnghe.bean.CompanyInfo;
import nhatnghe.bean.Mailer;

/**
 * @author Nhut Luu
 *
 */
@Controller
@RequestMapping("di")
public class DIController {
	@Autowired
	Mailer mailer;
	
//	@Autowired
	CompanyInfo company;
	
//	@Autowired
//	public DIController(CompanyInfo com) {
//		this.company = com;
//	}
	
	@Autowired @Qualifier("com2")
	public void setCompany(CompanyInfo com) {
		this.company = com;
	}
	
	@RequestMapping("index")
	public String index(ModelMap model) {
		model.addAttribute("congty", company);
		mailer.send();
		return "di";
	}
}
