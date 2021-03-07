package performance;

import java.util.Date;

public class StorageDTO {
	private int cnum;
	private int snum;
	private String mname;
	private int samount;
	private Date inputdate;
	
	public StorageDTO(int cnum,int snum,String mname,int samount,Date inputdate){
		super();
		this.cnum = cnum;
		this.snum = snum;
		this.mname = mname;
		this.samount = samount;
		this.inputdate = inputdate;
	}
	
	public int getCnum() {
		return cnum;
	}

	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	
	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum) {
		this.snum = snum;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public int getSamount() {
		return samount;
	}

	public void setSamount(int samount) {
		this.samount = samount;
	}

	public Date getInputdate() {
		return inputdate;
	}

	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}
}

