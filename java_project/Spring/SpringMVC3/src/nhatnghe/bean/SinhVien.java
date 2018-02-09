/**
 * 
 */
package nhatnghe.bean;

/**
 * @author Nhut Luu
 *
 */
public class SinhVien {
	String id;
	String fullname;
	Double marks;
	Boolean gender;
	String clazz;	
	
	public static boolean Male = true;
	public static boolean Female = false;
	
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Double getMarks() {
		return marks;
	}
	public void setMarks(Double marks) {
		this.marks = marks;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}	
}
