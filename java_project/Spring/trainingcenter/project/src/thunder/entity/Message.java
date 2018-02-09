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
@Table(name="message")
public class Message {
	@Id
	int id;
	String msg;
	long createDate;
	long sendDate;
			
	@Override
	public String toString() {
		return "Message [id=" + id + ", msg=" + msg + ", createDate=" + createDate + ", sendDate=" + sendDate + "]";
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDisplayCreateDate() {
		return converter.longToDate(createDate, "HH:mm EEE MMM dd yyyy");
	}

	public void setDisplayCreateDate(String createdDateString) {		
		createDate = converter.dateToLong(createdDateString, "HH:mm EEE MMM dd yyyy");
	}

	public String getDisplaySendDate() {
		return converter.longToDate(sendDate, "HH:mm EEE MMM dd yyyy");
	}

	public void setDisplaySendDate(String createdDateString) {
		sendDate = converter.dateToLong(createdDateString, "HH:mm EEE MMM dd yyyy");
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public long getSendDate() {
		return sendDate;
	}

	public void setSendDate(long sendDate) {
		this.sendDate = sendDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
