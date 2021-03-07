package zemi;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import performance.SalesDAO;
import performance.SalesDTO;
import performance.UserDTO;


public class CafeSales extends JPanel {
	JButton[] proBtn = new JButton[4];
	String[] proName = {"전체조회","기간 별 조회","메뉴 별 조회","이름 별 조회"};
	
	JButton[] updBtn = new JButton[1];
	String[] updName = {"삭제"};
	
	JButton[] inBtn = new JButton[1];
	String[] inn = {"총 매출 조회"};
	
	String[] colName = {"번호","이름","메뉴","가격","수량","결제금액","기간"};
	String[][] data;
	
	DefaultTableModel model2 = new DefaultTableModel(data,colName);
	JTable table2 = new JTable(model2);
	JTextField outTot = new JTextField(30);
	
	SalesDAO dao = new SalesDAO();
	
	class UBtn extends JPanel{
		UBtn(){
			setLayout(new GridLayout(1,2,3,3));
			setBackground(Color.WHITE);
			for(int i=0;i<updBtn.length;i++) {
				updBtn[i] = new JButton(updName[i]);
				add(updBtn[i]);
			}
		}
	}
	
	class IBtn extends JPanel{
		IBtn(){
			setLayout(new GridLayout(1,4,3,3));
			setBackground(Color.WHITE);
			for(int i=0;i<inBtn.length;i++) {
				inBtn[i] = new JButton(inn[i]);
				add(inBtn[i]);
			}
		}
	}
	
	class PBtn extends JPanel{
		PBtn(){
			setLayout(new GridLayout(4,1,3,3));
			setBackground(Color.WHITE);
			for(int i=0;i<proBtn.length;i++) {
				proBtn[i] = new JButton(proName[i]);
				add(proBtn[i]);
			}
		}
	}
	
	class Board2 extends JPanel{
		Board2(){
			setBackground(Color.WHITE);
			DefaultTableModel m2 = (DefaultTableModel)table2.getModel();
			table2.setRowHeight(50);
			table2.getTableHeader().setFont(new Font("맑은고딕", Font.BOLD, 15));
			add(new JScrollPane(table2));
		}
	}
	public CafeSales() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		Board2 bd2 = new Board2();
		PBtn pbtn = new PBtn();
		UBtn ubtn = new UBtn();
		IBtn ibtn = new IBtn();
		
		outTot.setSize(350, 70);
		outTot.setLocation(50, 480);
		add(outTot);
		
		ibtn.setSize(100, 70);
		ibtn.setLocation(400, 480);
		add(ibtn);
		
		bd2.setSize(500,600);
		bd2.setLocation(25,20);
		add(bd2);
		
		pbtn.setSize(400,430);
		pbtn.setLocation(530,23);
		add(pbtn);
		
		ubtn.setSize(400,70);
		ubtn.setLocation(530,480);
		add(ubtn);
		
		//전체 조회
		proBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=model2.getRowCount()-1;i>=0;i--) {
					model2.removeRow(i);
				}
				ArrayList<SalesDTO> list = dao.selectAll();
				for(SalesDTO dto:list) {
					Vector<Object> rowData =new Vector<>();
					rowData.add(dto.getNum());
					rowData.add(dto.getName());
					rowData.add(dto.getMenu());
					rowData.add(dto.getPrice());
					rowData.add(dto.getAmount());
					rowData.add(dto.getTot());
					rowData.add(dto.getRegdate());
					model2.addRow(rowData);
				}
				dao.nullChange();
			}
		});
		
		//기간 별 조회
		proBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String st
				= JOptionPane.showInputDialog(CafeSales.this, "처음 날짜를 입력하세요 (YYYY/MM/DD)", "가입기간 내 검색",JOptionPane.PLAIN_MESSAGE);
				if(st!=null) {
					String et
					= JOptionPane.showInputDialog(CafeSales.this, "마지막 날짜를 입력하세요 (YYYY/MM/DD)", "가입기간 내 검색",JOptionPane.PLAIN_MESSAGE);
					if(et!=null) {
						for(int i=model2.getRowCount()-1;i>=0;i--) {
							model2.removeRow(i);
						}
						ArrayList<SalesDTO> list = dao.selectDate(st,et);
						if(list.size()!=0) {
							for(SalesDTO dto:list) {
								Vector<Object> rowData =new Vector<>();
								rowData.add(dto.getNum());
								rowData.add(dto.getName());
								rowData.add(dto.getMenu());
								rowData.add(dto.getPrice());
								rowData.add(dto.getAmount());
								rowData.add(dto.getTot());
								rowData.add(dto.getRegdate());
								model2.addRow(rowData);	
							}
						}else {
							JOptionPane.showMessageDialog(CafeSales.this, "찾는 기록이 존재하지 않습니다");
						}
					}
				}
			}
		});
		
		//메뉴로 조회
		proBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String menu
				= JOptionPane.showInputDialog(CafeSales.this, "메뉴이름을 입력하세요", "메뉴이름으로 검색",JOptionPane.PLAIN_MESSAGE);
				ArrayList<SalesDTO> list = dao.selectMenu(menu);
				if(menu!=null) {
					if(list.size()!=0) {
						for(int i=model2.getRowCount()-1;i>=0;i--) {
							model2.removeRow(i);
						}
						for(SalesDTO dto:list) {
							Vector<Object> rowData =new Vector<>();
							rowData.add(dto.getNum());
							rowData.add(dto.getName());
							rowData.add(dto.getMenu());
							rowData.add(dto.getPrice());
							rowData.add(dto.getAmount());
							rowData.add(dto.getTot());
							rowData.add(dto.getRegdate());
							model2.addRow(rowData);	
						}
					}else{
						JOptionPane.showMessageDialog(CafeSales.this, "찾는 기록이 존재하지 않습니다");
					}
				}
			}
		});
		
		//이름으로 조회
		proBtn[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name
				= JOptionPane.showInputDialog(CafeSales.this, "이름을 입력하세요", "이름으로 검색",JOptionPane.PLAIN_MESSAGE);
				ArrayList<SalesDTO> list = dao.selectName(name);
				if(name!=null) {
					if(list.size()!=0) {
						for(int i=model2.getRowCount()-1;i>=0;i--) {
							model2.removeRow(i);
						}
						for(SalesDTO dto:list) {
							Vector<Object> rowData =new Vector<>();
							rowData.add(dto.getNum());
							rowData.add(dto.getName());
							rowData.add(dto.getMenu());
							rowData.add(dto.getPrice());
							rowData.add(dto.getAmount());
							rowData.add(dto.getTot());
							rowData.add(dto.getRegdate());
							model2.addRow(rowData);	
						}
					}else{
						JOptionPane.showMessageDialog(CafeSales.this, "찾는 기록이 존재하지 않습니다");
					}
				}
			}
		});		
		
		// 매출 조회
		inBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowCont = table2.getRowCount();
				int sum =0;
				for(int i=0;i<rowCont;i++) {
					sum += (int)table2.getValueAt(i, 5);
				}
				outTot.setText(String.valueOf(" 총 금액 : "+sum));
				outTot.setFont(new Font("맑은고딕", Font.BOLD, 40));
			}
		});
		
		//삭제
		updBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				DefaultTableModel m = (DefaultTableModel)table2.getModel();
				int num = (int)m.getValueAt(table2.getSelectedRow(), 0);
				int btn = JOptionPane.showConfirmDialog(CafeSales.this,"정말로 삭제하시겠습니까?");
				if(btn==JOptionPane.YES_OPTION) {
					m.removeRow(table2.getSelectedRow());
					dao.delete(num);
				}
				}catch(Exception ee) {
					
				}
			}
		});
	}
}
