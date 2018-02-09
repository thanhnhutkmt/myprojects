/**
 * 
 */
package thunder.bean;

import thunder.util.converter;

/**
 * @author java_dev
 *
 */
public class ShowCourses {	
	String timeTable;
	String room;
	String openDate;	
	String fee;
	
	public static ShowCourses objectToThis(Object o) {		
		ShowCourses sc = new ShowCourses();
		Object [] res = (Object[]) o;
		sc.setTimeTable(res[0].toString());				
		sc.setRoom(res[1].toString());
		sc.setOpenDate(converter.longToDate(
			Long.parseLong(res[2].toString()), "dd/MM/yyyy"));
		sc.setFee(res[3].toString());
		System.out.println(sc);
		return sc;
	}
	
	@Override
	public String toString() {
		return "ShowCourses [timeTable=" + timeTable + ", room=" + room + ", openDate=" + openDate + ", fee=" + fee
				+ "]";
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
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}			
}
