/**
 * 
 */
package thunder.bean;

import thunder.util.converter;

/**
 * @author java_dev
 *
 */
public class RegCourses {
	String title;
	String timeTable;
	String room;
	String openDate;
	String deposit;
	String fee;
	String feeStatus;
	
	public static RegCourses objectToThis(Object o) {		
		RegCourses rc = new RegCourses();
		Object [] res = (Object[]) o;
		
		rc.setTitle(res[0].toString());		
		rc.setTimeTable(res[1].toString());		
		rc.setRoom(res[2].toString());
		rc.setOpenDate(converter.longToDate(
			Long.parseLong(res[3].toString()), "dd/MM/yyyy"));
		rc.setDeposit(res[4].toString());		
		rc.setFee(res[5].toString());		
		rc.setFeeStatus(res[6].toString());
		System.out.println(rc);
		return rc;
	}
	
	@Override
	public String toString() {
		return "RegCourses [title=" + title + ", timeTable=" + timeTable + ", room=" + room + ", openDate=" + openDate
				+ ", deposit=" + deposit + ", fee=" + fee + ", feeStatus=" + feeStatus + "]";
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getFeeStatus() {
		return feeStatus;
	}
	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}	
}
