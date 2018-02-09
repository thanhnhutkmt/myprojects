/**
 * 
 */
package thunder.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author java_dev
 *
 */
@Entity
@Table(name="favcourses")
public class Favcourses {
	@Id
	int favid;
	int courseid, learnerid;
	@ManyToOne()
	@JoinColumn(name="learnerId", insertable=false, updatable=false)
	private Learners learners;
	@ManyToOne()
	@JoinColumn(name="courseid", insertable=false, updatable=false)
	private Courses courses;

	public Favcourses() {
		this.courseid = -1;
		this.learnerid = -1;
	}
	
	public Favcourses(int courseid, int learnerid) {
		this.courseid = courseid;
		this.learnerid = learnerid;
	}
	
	@Override
	public String toString() {
		return "FavCourses [favid=" + favid + ", courseid=" + courseid + ", learnerid=" + learnerid + "]";
	}
		
	public Learners getLearners() {
		return learners;
	}

	public void setLearners(Learners learners) {
		this.learners = learners;
	}

	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	public int getFavid() {
		return favid;
	}

	public void setFavid(int favid) {
		this.favid = favid;
	}

	public int getCourseid() {
		return courseid;
	}

	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	public int getLearnerid() {
		return learnerid;
	}

	public void setLearnerid(int learnerid) {
		this.learnerid = learnerid;
	}	
}
