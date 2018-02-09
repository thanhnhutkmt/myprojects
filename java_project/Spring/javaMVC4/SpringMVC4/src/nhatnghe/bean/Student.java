package nhatnghe.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class Student {
	@Length(min=5, message="Ít nhất 5 ký tự")
	String x;
	
	@Range(min=16, max=65, message="Từ 16 đến 65")
	Integer age;
	
	@NotEmpty(message="Không để trống")
	@Email(message="Không đúng dạng email")
	String email;
	
	public String getName() {
		return x;
	}
	public void setName(String name) {
		this.x = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
