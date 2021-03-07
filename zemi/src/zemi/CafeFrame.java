package zemi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class CafeFrame extends JFrame{
	JTabbedPane tab = new JTabbedPane();
	JPanel mainPane = new JPanel();
	JPanel admiPane = new JPanel();
	JPanel userPane = new JPanel();
	JPanel storPane = new JPanel();
	JPanel couPane = new JPanel();
	JLabel lab = new JLabel("dsfaasdf");
	
	public CafeFrame() {
		
		
		setTitle("CafePOS");
		mainPane.setLayout(new BorderLayout());
		mainPane.add(new CafeMain(),"Center");
		admiPane.setLayout(new BorderLayout());
		admiPane.add(new CafeSales(),"Center");
		userPane.setLayout(new BorderLayout());
		userPane.add(new CafeUser(),"Center");
		storPane.setLayout(new BorderLayout());
		storPane.add(new CafeStorage(),"Center");
		couPane.setLayout(new BorderLayout());
		couPane.add(new CafeCoupon(),"Center");
		
		tab.add("��� ȭ��",mainPane);
		tab.add("���� ����",admiPane);
		tab.add("ȸ�� ����",userPane);
		tab.add("��� ����",storPane);
		tab.add("���� ��Ȳ",couPane);
		
		setLayout(new BorderLayout());
		add(tab,"Center");
		setSize(1000,660);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}
}

