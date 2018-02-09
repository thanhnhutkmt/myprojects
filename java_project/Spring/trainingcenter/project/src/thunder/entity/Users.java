package thunder.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import thunder.util.converter;

@Entity
@Table(name="users")
public class Users {    
	@Id
	String userid;
	String pwd;	
	String role;
	String email;
	long createdDate;
	
	@Override
	public String toString() {
		return "Users [userid=" + userid + ", pwd=" + pwd + 
				", role=" + role + ", email=" + email 
				+ ", createdDate=" + createdDate + "]";
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}		
	
	public String getCreatedDateDisplay() {
		return converter.longToDate(createdDate, "HH:mm EEE MMM dd yyyy");
	}

	public void setCreatedDateDisplay(String datestring) {
		this.createdDate = converter.dateToLong(datestring, "HH:mm EEE MMM dd yyyy");
	}
}
