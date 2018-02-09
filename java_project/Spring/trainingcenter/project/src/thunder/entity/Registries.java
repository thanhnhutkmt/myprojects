package thunder.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import thunder.util.converter;

@Entity
@Table(name="registries")
public class Registries {
	@Id
	int registryID;	
	int classID;	
	int learnerID;
	long registeredDate;
	String deposit;
	boolean feeStatus;	
	@ManyToOne()
	@JoinColumn(name="learnerid", insertable=false, updatable=false)
	private Learners learners;
	@ManyToOne()
	@JoinColumn(name="classid", insertable=false, updatable=false)
	private Classes classes;

    @Override
	public String toString() {
		return "Registries [registryID=" + registryID + ", classID=" + classID + ", learnerID=" + learnerID
				+ ", registeredDate=" + registeredDate + ", deposit=" + deposit + ", feeStatus=" + feeStatus + "]";
	}
       
	public Learners getLearners() {
		return learners;
	}

	public void setLearners(Learners learners) {
		this.learners = learners;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public int getRegistryID() {
		return registryID;
	}

	public void setRegistryID(int registryID) {
		this.registryID = registryID;
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

	public long getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(long registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public boolean isFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(boolean feeStatus) {
		this.feeStatus = feeStatus;
	}

	public void setDisplayValue(String createdDateString) {
    	registeredDate = converter.dateToLong(createdDateString, "HH:mm EEE MMM dd yyyy");
    }
	
    public String getDisplayValue() {
        return converter.longToDate(registeredDate, "HH:mm EEE MMM dd yyyy");
    } 	
    
	public void setFeeStatusDisplay(String feeStatusDisplay) {
    	feeStatus = feeStatusDisplay.equals("Xong") ? true : false;
    }
	
    public String getFeeStatusDisplay() {
        return (feeStatus) ? "Xong" : "Chưa xong";
    } 
}
