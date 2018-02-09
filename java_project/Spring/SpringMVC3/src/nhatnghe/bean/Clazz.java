/**
 * 
 */
package nhatnghe.bean;

/**
 * @author Nhut Luu
 *
 */
public class Clazz {
	String id;
	String name;
	
	public Clazz() {
		super();
	}
	
	public Clazz(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
