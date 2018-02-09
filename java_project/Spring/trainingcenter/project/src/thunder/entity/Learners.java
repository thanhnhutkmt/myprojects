package thunder.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import thunder.util.converter;

@Entity
@Table(name="learners")
public class Learners {
	@Id
	int learnerId;	
	String lastName, middleName, firstName;
	long birthDate, createdDate;
	String address, email1, email2, email3;
	String mobile, homePhone, workPhone, iDcard, iDpassport, notes;
	String image;
	boolean sex;
	@OneToMany(mappedBy="learners")	
	private Collection<Favcourses> favcourses;
	@OneToMany(mappedBy="learners")
	private Collection<Registries> registries;
	@ManyToMany(mappedBy="learners")
	private Collection<Examination> examination;
	
	@Override
	public String toString() {
		return "Learners [LearnerId=" + learnerId + ", lastName=" + lastName + ", middleName=" + middleName
				+ ", firstName=" + firstName + ", birthDate=" + birthDate + ", createdDate=" + createdDate
				+ ", address=" + address + ", sex=" + sex + ", email1=" + email1 + ", email2=" + email2 + ", email3="
				+ email3 + ", mobile=" + mobile + ", homePhone=" + homePhone + ", workPhone=" + workPhone + ", iDcard="
				+ iDcard + ", iDpassport=" + iDpassport + ", notes=" + notes + ", image=" + image
				+ "]";
	}
				
	public Collection<Favcourses> getFavcourses() {
		return favcourses;
	}

	public void setFavcourses(Collection<Favcourses> favcourses) {
		this.favcourses = favcourses;
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

	public int getLearnerId() {
		return learnerId;
	}

	public void setLearnerId(int learnerId) {
		this.learnerId = learnerId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(long birthDate) {
		this.birthDate = birthDate;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public String getSexDisplay() {
		return (sex) ? "Nam" : "Nữ";
	}

	public void setSexDisplay(String sexstring) {
		this.sex = (sexstring == null || sexstring.equals("Nam")) ? true : false;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getiDcard() {
		return iDcard;
	}

	public void setiDcard(String iDcard) {
		this.iDcard = iDcard;
	}

	public String getiDpassport() {
		return iDpassport;
	}

	public void setiDpassport(String iDpassport) {
		this.iDpassport = iDpassport;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = (image == null || image.length() == 0) ? 
			((isSex()) ? "male_default.jpg" : "female_default.jpg") 
			: image;
	}

	public void setbirthDateString(String DateString) {
    	birthDate = converter.dateToLong(DateString, "dd/MM/yyyy");
    }
	
    public String getbirthDateString() {
        return converter.longToDate(birthDate, "dd/MM/yyyy");
    }   

	public void setcreatedDateString(String DateString) {
        createdDate = converter.dateToLong(DateString, "HH:mm EEE MMM dd yyyy");
    }
	
    public String getcreatedDateString() {
        return converter.longToDate(createdDate);
    }    
}
