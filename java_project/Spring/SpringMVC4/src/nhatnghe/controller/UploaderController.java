/**
 * 
 */
package nhatnghe.controller;



import javax.servlet.ServletContext;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Nhut Luu
 *
 */
@Controller
@RequestMapping("uploader")
public class UploaderController {
	@RequestMapping("index")
	public String index() {
		return "uploader/index";
	}
	
	@Autowired
	ServletContext app;
	@RequestMapping("upload")
	public String upload(ModelMap model,
			@RequestParam("image") MultipartFile image) {
	String filename = image.getOriginalFilename();
	String path = app.getRealPath("/images/"+filename);
	try {
		image.transferTo(new File(path));
		model.addAttribute("message", "Lưu file thành công: " + path);
		
		model.addAttribute("filename", filename);
		model.addAttribute("filesize", image.getSize());
		model.addAttribute("filetype", image.getContentType());
	} 
	catch (Exception e) {
		model.addAttribute("message", "Không lưu được file");
	}
	
	return "uploader/success";
	}
}
