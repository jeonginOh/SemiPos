package zemi;

public class test01 {
	public static void main(String[] args) {
		
			String[] menu = {"����123","��1��11","����111���ֱ��ֱ�"};
			for(int i = 0; i<menu.length;i++) {
			String intStr = menu[i].replaceAll("[^\\d]", "");
			System.out.println(intStr);
			}
		}
}
