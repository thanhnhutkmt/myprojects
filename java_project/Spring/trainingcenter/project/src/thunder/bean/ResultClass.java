/**
 * 
 */
package thunder.bean;

/**
 * @author java_dev
 *
 */
public class ResultClass {
	private Long startDate;
	private String timeTable;
	private String room;
	private String description;
	private String title;		
	
	public static ResultClass objectToThis(Object o) {		
		ResultClass rc = new ResultClass();
		//openDate
		Object [] res = (Object[]) o;
		rc.setStartDate(Long.parseLong(res[0].toString()));
		rc.setTimeTable(res[1].toString());
		rc.setRoom(res[2].toString());
		rc.setTitle(res[3].toString());
		rc.setDescription(res[4].toString());
		System.out.println(rc);
		return rc;
	}
	
	@Override
	public String toString() {
		return "ResultClass [startDate=" + startDate + ", timeTable=" + timeTable + ", room=" + room + ", description="
				+ description + ", title=" + title + "]";
	}
	
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
