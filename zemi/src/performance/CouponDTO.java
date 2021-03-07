package performance;

import java.util.Date;

public class CouponDTO {
	private String counum;
	private String couval;
	private int saleprice;
	private Date lastdate;
	private int days;
	
	public CouponDTO(String counum,String couval,int saleprice,Date lastdate,int days) {
		super();
		this.counum = counum;
		this.couval = couval;
		this.saleprice = saleprice;
		this.lastdate = lastdate;
		this.days = days;
	}

	public String getCounum() {
		return counum;
	}

	public void setCounum(String counum) {
		this.counum = counum;
	}

	public String getCouval() {
		return couval;
	}

	public void setCouval(String couval) {
		this.couval = couval;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
	
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
	
}
