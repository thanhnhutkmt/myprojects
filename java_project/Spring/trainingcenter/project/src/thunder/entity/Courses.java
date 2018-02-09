package thunder.entity;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;

import thunder.bean.ResultClass;
import thunder.util.converter;

@Entity
@Table(name="courses")
public class Courses {
	@Id
	int courseid;	
	String title;	
	long createdDate;
	String groupOfCourse;
	String description;
	String fee;
	String duration;	
	@OneToMany(mappedBy="course")		
	Collection<Classes> classes;
	@OneToMany(mappedBy="courses")	
	Collection<Favcourses> favcourses;
	
	@Override
	public String toString() {
		return "Courses [courseid=" + courseid + ", title=" + title + ", createdDate=" + createdDate
				+ ", groupOfCourse=" + groupOfCourse + ", description=" + description + ", fee=" + fee + ", duration="
				+ duration + "]";
	}	
		
	
	public Collection<Classes> getClasses() {
		return classes;
	}

	public void setClasses(Collection<Classes> classes) {
		this.classes = classes;
	}

	public Collection<Favcourses> getFavcourses() {
		return favcourses;
	}

	public void setFavcourses(Collection<Favcourses> favcourses) {
		this.favcourses = favcourses;
	}

	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getGroupOfCourse() {
		return groupOfCourse;
	}
	public void setGroupOfCourse(String groupOfCourse) {
		this.groupOfCourse = groupOfCourse;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee.replace(",", "");
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
    public void setDisplayValue(String createdDateString) {
        createdDate = converter.dateToLong(createdDateString, "HH:mm EEE MMM dd yyyy");
    }
	
    public String getDisplayValue() {
        return converter.longToDate(createdDate);
    }  
}
