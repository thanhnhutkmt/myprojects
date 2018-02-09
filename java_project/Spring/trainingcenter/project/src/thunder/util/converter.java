/**
 * 
 */
package thunder.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author java_dev
 *
 */
public class converter {
	public static String longToDate(long time, String pattern) {
		if (time == 0) return "";
		return new SimpleDateFormat(pattern).format(new Date(time));
	}
	
	public static String longToDate(long time) {
		if (time == 0) return "";
		return new SimpleDateFormat("HH:mm EEE MMM dd yyyy").format(new Date(time));
	}
	
	public static long dateToLong(String datestring, String pattern) {
		if (datestring == null || datestring.trim().length() == 0) return System.currentTimeMillis();
		try {			
			return new SimpleDateFormat(pattern).parse(datestring).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static String genRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		char dataset[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 
				'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', 
				'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_', '-', '=', '+' 
		};
		while(length-- > 0) sb.append(dataset[(int) (Math.random()*(dataset.length - 1))]);
		return sb.toString();
	}
	
	public static String getUsernameFromCookie(HttpServletRequest request) {
		Cookie c[] = request.getCookies();
		for (int i = 0; i < c.length; i++) {
			if (c[i].getName().equals("username")) return c[i].getValue();
		}
		return "";
	}	
}
