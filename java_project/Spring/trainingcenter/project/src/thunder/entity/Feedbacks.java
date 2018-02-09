/**
 * 
 */
package thunder.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import thunder.util.converter;

/**
 * @author java_dev
 *
 */
@Entity
@Table(name="feedbacks")
public class Feedbacks {
	@Id
	int feedbackID;
	String fullName;
	String phone;
	String email;
	String title;
	String content;
	long submittedDate;
		
	@Override
	public String toString() {
		return "FeedBack [feedbackID=" + feedbackID + ", name=" + fullName 
				+ ", phone=" + phone + ", email=" + email
				+ ", title=" + title + ", content=" + content + "]";
	}
	
	public long getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(long submittedDate) {
		this.submittedDate = submittedDate;
	}
	
	public String getSubmittedDateDisplay() {
		return converter.longToDate(submittedDate, "HH:mm EEE MMM dd yyyy");
	}

	public void setSubmittedDateDisplay(String datestring) {
		this.submittedDate = converter.dateToLong(datestring, "HH:mm EEE MMM dd yyyy");
	}	

	public int getFeedbackID() {
		return feedbackID;
	}
	public void setFeedbackID(int feedbackID) {
		this.feedbackID = feedbackID;
	}
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
}
