/**
 * 
 */
package thunder.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author Nhut Luu
 *
 */
public class AuthenticateInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean ok = true;
		Cookie c[] = request.getCookies(); 
		boolean loggedin = false; 		
		int i = 0;
		String ouri = request.getRequestURI();
		if (ouri.contains("/service")) return true;
		
		for (; i < c.length; i++)
			if (c[i].getName().equals("username")) {loggedin = true; break;}			
		if (loggedin) {
			if (adminOnly(ouri) && !c[i].getValue().equals("admin")) { 
				ok = false;
				redirect(request, response, "/TrainingCenter/adminonly.jsp");
			}
		} else {					
			if (!exclude(ouri)) {
				ok = false;
				redirect(request, response, "/TrainingCenter/signin.jsp");
			}
		}	
		return ok;
	}
	
	public void redirect(HttpServletRequest request, 
			HttpServletResponse response, String url) throws IOException {		
		String uri = request.getRequestURI().replace(request.getContextPath(), "");
		System.out.println("forbid " + uri);
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("uri", uri);			
		response.sendRedirect(url);
	}
	public boolean exclude(String ouri) {
		List<String> ex_uri = new ArrayList<String>();
		ex_uri.add("/TrainingCenter/registerlearner.controller");
		ex_uri.add("/TrainingCenter/lienhe.controller");
		ex_uri.add("/TrainingCenter/sendfeedback.controller");
		ex_uri.add("/TrainingCenter/loginresult.controller");		
		return ex_uri.contains(ouri);
	}
	
	public boolean adminOnly(String ouri) {
		List<String> ex_uri = new ArrayList<String>();
		ex_uri.add("/TrainingCenter/insert");
		ex_uri.add("/TrainingCenter/update");
		ex_uri.add("/TrainingCenter/delete");
		ex_uri.add("/TrainingCenter/edit");
		ex_uri.add("/TrainingCenter/courses");
		ex_uri.add("/TrainingCenter/classes");
		ex_uri.add("/TrainingCenter/exams");
		ex_uri.add("/TrainingCenter/learners");
		ex_uri.add("/TrainingCenter/registries");
		ex_uri.add("/TrainingCenter/users");
		for (int i = 0; i < ex_uri.size(); i++) 
			if (ouri.startsWith(ex_uri.get(i))) return true;
		return false;
	}
}
