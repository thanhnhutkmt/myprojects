package thunder.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import thunder.util.converter;


@Entity
@Table(name="examination")
public class Examination {
	@Id
	int examID;
	int classID;	
	int learnerID;	
	long examDate;
	String mark, resultType, examRoom;
	boolean result;	
	@ManyToMany()
	@JoinColumn(name="learnerId", insertable=false, updatable=false)
	Collection<Learners> learners;
	@ManyToOne()
	@JoinColumn(name="classid", insertable=false, updatable=false)
	private Classes classes;
	
    @Override
	public String toString() {
		return "Exams [examID=" + examID + ", classID=" + classID + ", learnerID=" + learnerID + ", examDate="
				+ examDate + ", mark=" + mark + ", resultType=" + resultType + ", examRoom=" + examRoom + ", result="
				+ result + "]";
	}

    
	public Collection<Learners> getLearners() {
		return learners;
	}

	public void setLearners(Collection<Learners> learners) {
		this.learners = learners;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public int getExamID() {
		return examID;
	}

	public void setExamID(int examID) {
		this.examID = examID;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public int getLearnerID() {
		return learnerID;
	}

	public void setLearnerID(int learnerID) {
		this.learnerID = learnerID;
	}

	public long getExamDate() {
		return examDate;
	}

	public void setExamDate(long examDate) {
		this.examDate = examDate;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(String examRoom) {
		this.examRoom = examRoom;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setDisplayValue(String createdDateString) {
        examDate = converter.dateToLong(createdDateString, "HH:mm EEE MMM dd yyyy");
    }
	
    public String getDisplayValue() {
        return converter.longToDate(examDate, "HH:mm EEE MMM dd yyyy");
    } 	    
    
	public String getResultDisplay() {
		return (result) ? "Đậu" : "Rớt";
	}

	public void setResultDisplay(String mark) {
		try {
			this.result = (Double.parseDouble(this.mark) >= 5);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
