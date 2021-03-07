package zemi;

public class test01 {
	public static void main(String[] args) {
		
			String[] menu = {"으앙123","쥬1금11","안주111금주금주금"};
			for(int i = 0; i<menu.length;i++) {
			String intStr = menu[i].replaceAll("[^\\d]", "");
			System.out.println(intStr);
			}
		}
}
