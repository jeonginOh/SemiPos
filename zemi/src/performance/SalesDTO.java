package performance;

import java.util.Date;

public class SalesDTO {
	private int num;
	private String name;
	private String menu;
	private int price;
	private int amount;
	private int tot;
	private Date regdate;
	
	public SalesDTO(int num, String name, String menu, int price, int amount, int tot, Date regdate) {
		super();
		this.num = num;
		this.name = name;
		this.menu = menu;
		this.price = price;
		this.amount = amount;
		this.tot = tot;
		this.regdate = regdate;
	}

	public int getTot() {
		return tot;
	}

	public void setTot(int tot) {
		this.tot = tot;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
}
