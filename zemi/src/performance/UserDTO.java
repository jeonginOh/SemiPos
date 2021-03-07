package performance;

import java.util.Date;

public class UserDTO {
	private String name;
	private String phone;
	private int point;
	private Date indate;
	
	public UserDTO(String name, String phone, int point, Date indate) {
		super();
		this.name = name;
		this.phone = phone;
		this.point = point;
		this.indate = indate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}
	
}
