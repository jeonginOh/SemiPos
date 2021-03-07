package zemi;

import java.awt.Color;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import performance.CouponDAO;
import performance.CouponDTO;
import performance.SalesDAO;
import performance.SalesDTO;
import performance.UserDAO;
import performance.UserDTO;

public class CafeMain extends JPanel {
	JButton[] menuBtn = new JButton[18];
	String[] menu = {
			"아메리카노","카페라떼","카페모카",
			"카푸치노","바닐라라떼","녹차라떼",
			"얼그레이","카모마일","루이보스",
			"아이스티","핫초코","아포가토",
			"티라미수","치즈케익","바닐라케익",
			"+","-","포인트 사용"};
	int[] price = {
			2000,2500,3000,
			3000,3500,3500,
			4000,4000,4500,
			2500,3000,3500,
			5000,5000,5500};
	JTextField outTot = new JTextField(30);
	JButton[] selBtn = new JButton[4];
	String[] sel = {"쿠폰","선택취소","전체취소","결제"};
	String[] colName = {"메뉴","수량","가격"};
	String [][] data ;
	int count =1;
	DefaultTableModel model = new DefaultTableModel(data,colName);
	JTable table = new JTable(model);
	
	class Board extends JPanel{
		Board(){
			setBackground(Color.WHITE);
			DefaultTableModel m = (DefaultTableModel)table.getModel();
			table.setRowHeight(50);
			table.getTableHeader().setFont(new Font("맑은고딕", Font.BOLD, 15));
			add(new JScrollPane(table));
		}
	}
	
	class MBtn extends JPanel{
		MBtn(){
			setLayout(new GridLayout(6,3,3,3));
			setBackground(Color.WHITE);
			for(int i=0;i<menuBtn.length;i++) {
				menuBtn[i]= new JButton(menu[i]);
				add(menuBtn[i]);
			}
		}
	}
	
	class SBtn extends JPanel{
		SBtn(){
			setBackground(Color.WHITE);
			setLayout(new GridLayout(1,4,3,3));
			
			for(int i=0;i<sel.length;i++) {
				selBtn[i]= new JButton(sel[i]);
				add(selBtn[i]);
			}
		}
	}
	
	public CafeMain(){
		setLayout(null);
		setBackground(Color.WHITE);
		MBtn mbtn = new MBtn();
		SBtn sbtn = new SBtn();
		Board bd = new Board();
		
		outTot.setSize(450, 70);
		outTot.setLocation(50, 480);
		add(outTot);
		
		bd.setSize(500, 500);
		bd.setLocation(25, 20);
		add(bd);
		
		mbtn.setSize(400, 430);
		mbtn.setLocation(530, 23);
		add(mbtn);	
		
		sbtn.setSize(400, 70);
		sbtn.setLocation(530, 480);
		add(sbtn);
		
		//메뉴 추가 버튼
		for(int i=0;i<15;i++) {
			final int a =i;
			menuBtn[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					m.addRow(new Object[]{menu[a],count,price[a]});
				}
			});
		}
		//갯수 추가버튼
		menuBtn[15].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				DefaultTableModel m = (DefaultTableModel)table.getModel();
				int a = table.getSelectedRow();
				int c = (int)table.getValueAt(a, 1);
				table.setValueAt(c+1,a,1);
				}catch(Exception ee) {
				}
			}
		});
		
		//갯수 삭제버튼
		menuBtn[16].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					int a = table.getSelectedRow();
					int c = (int)table.getValueAt(a, 1);
					table.setValueAt(c-1,a,1);
				}catch(Exception ee) {
					
				}
			}
		});
		//포인트 사용버튼
		menuBtn[17].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String name = JOptionPane.showInputDialog(CafeMain.this, "회원 이름을 입력하세요", "포인트 사용",JOptionPane.PLAIN_MESSAGE);
					if(name!=null) {
						UserDAO daoU = new UserDAO();
						UserDTO dtoU = daoU.selectUserName(name);
						if(dtoU!=null) {
							String poi = JOptionPane.showInputDialog(CafeMain.this, "사용할 포인트를 입력해주세요", "포인트 사용",JOptionPane.PLAIN_MESSAGE);
							int point = Integer.parseInt(poi);
							if(point>dtoU.getPoint()) {
								JOptionPane.showMessageDialog(CafeMain.this, "포인트가 부족합니다");
							}else {
								m.addRow(new Object[]{"포인트 사용",1,point*(-1)});
							}
						}else {
							JOptionPane.showMessageDialog(CafeMain.this, "조회한 회원이 존재하지 않습니다");
						}
					}
				}catch(Exception ee) {
					
				}
			}
		});
		
		
		//쿠폰 사용 버튼
		selBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CouponDAO dao = new CouponDAO();
					String counum = JOptionPane.showInputDialog(CafeMain.this, "쿠폰번호를 입력하세요", "쿠폰 번호 입력",JOptionPane.PLAIN_MESSAGE);
					if(counum!=null) {
						DefaultTableModel m = (DefaultTableModel)table.getModel();
						CouponDTO dto = dao.selectC(counum);
						if(dto!=null) {
							m.addRow(new Object[]{dto.getCouval()+"("+dto.getCounum()+")",count,dto.getSaleprice()*(-1)});
						}else {
							JOptionPane.showMessageDialog(CafeMain.this, "없는 쿠폰번호입니다");
						}
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeMain.this, "잘못된 접근입니다");
				}
			}
		});
		
		//취소버튼
		selBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					m.removeRow(table.getSelectedRow());
				}catch(Exception ee) {
					
				}
			}
		});
		
		//전체 취소 버튼
		selBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel m = (DefaultTableModel)table.getModel();
				
				m.setRowCount(0);
				outTot.setText(String.valueOf(""));
			}
		});
		
		//결제 버튼
		selBtn[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel m = (DefaultTableModel)table.getModel();
				int sum =0;
				int rowCont = table.getRowCount();
				
				for(int i=0;i<rowCont;i++) {
					sum += (int)table.getValueAt(i, 2);
				}
				
				outTot.setText(String.valueOf(" Total : "+sum));
				outTot.setFont(new Font("맑은고딕", Font.BOLD, 40));
				
				String name = JOptionPane.showInputDialog(CafeMain.this, "회원 이름을 입력하세요", "결제 화면",JOptionPane.PLAIN_MESSAGE);
				if(name!=null) {
					SalesDAO daoS = new SalesDAO();
					CouponDAO daoC = new CouponDAO();
					UserDAO daoU = new UserDAO();
					UserDTO dtoU = daoU.selectUserName(name);
					
					if(dtoU!=null) {
						for(int i=0;i<rowCont;i++) {
							String menu= (String)table.getValueAt(i, 0);
							int price = (int)table.getValueAt(i, 2);
							int amount = (int)table.getValueAt(i, 1);
							int tot = price*amount;
							SalesDTO dtoS = new SalesDTO(0,name,menu,price,amount,tot,null);
							String counum = menu.replaceAll("[^\\d]", "");
							if(menu.equals("포인트 사용")) {
								daoU.point(dtoU, price);
							}
							if(dtoS!=null) {
								CouponDTO dtoC = daoC.selectC(counum);
								daoS.insert(dtoS);
								if(dtoC !=null) {
									daoC.deleteCoupon(counum);
									}
								}
						}
						
						m.setRowCount(0);
						JOptionPane.showMessageDialog(CafeMain.this, "결제 완료");
						outTot.setText(String.valueOf(""));
					}else {
						JOptionPane.showMessageDialog(CafeMain.this, "다시 입력해주세요");
					}
					Double po = sum*0.05;
					daoU.point(dtoU, po);
				}
			}
		});
	}
}
