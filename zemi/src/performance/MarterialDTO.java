package performance;

public class MarterialDTO {
	private int inum;
	private String mname;

	private String ifrom;
	private String howuse;
	
	public MarterialDTO
	(int inum,String mname,String ifrom,String howuse) {
		super();
		this.inum = inum;
		this.mname = mname;
		this.ifrom = ifrom;
		this.howuse = howuse;
	}

	public int getInum() {
		return inum;
	}

	public void setInum(int inum) {
		this.inum = inum;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getIfrom() {
		return ifrom;
	}

	public void setIfrom(String ifrom) {
		this.ifrom = ifrom;
	}

	public String getHowuse() {
		return howuse;
	}

	public void setHowuse(String howuse) {
		this.howuse = howuse;
	}
}