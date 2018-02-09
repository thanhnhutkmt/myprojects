package thunder.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import thunder.util.converter;

@Entity
@Table(name="classes")
public class Classes {
	@Id
	int classid;	
	int courseid;	
	long openDate;
	String timeTable;
	String room;	
	@ManyToOne()
	@JoinColumn(name="courseid", insertable=false, updatable=false)
	private Courses course;	
	@OneToMany(mappedBy="classes")	
	private Collection<Registries> registries;
	@OneToMany(mappedBy="classes")	
	private Collection<Examination> examination;
	
	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public Collection<Registries> getRegistries() {
		return registries;
	}

	public void setRegistries(Collection<Registries> registries) {
		this.registries = registries;
	}

	public Collection<Examination> getExamination() {
		return examination;
	}

	public void setExamination(Collection<Examination> examination) {
		this.examination = examination;
	}

	public String getCourseName() {
		return course.getTitle();
	}
	
	@Override
	public String toString() {
		return "Classes [classid=" + classid + ", courseid=" + courseid + ", openDate=" + openDate + ", timeTable="
				+ timeTable + ", room=" + room + "]";
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public long getOpenDate() {
		return openDate;
	}
	public void setOpenDate(long openDate) {
		this.openDate = openDate;
	}
	public String getTimeTable() {
		return timeTable;
	}
	public void setTimeTable(String timeTable) {
		this.timeTable = timeTable;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
    public void setDisplayValue(String createdDateString) {
        openDate = converter.dateToLong(createdDateString, "dd/MM/yyyy");
    }
	
    public String getDisplayValue() {
        return converter.longToDate(openDate, "dd/MM/yyyy");
    } 	    
}
