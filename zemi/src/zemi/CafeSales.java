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
	String[] proName = {"��ü��ȸ","�Ⱓ �� ��ȸ","�޴� �� ��ȸ","�̸� �� ��ȸ"};
	
	JButton[] updBtn = new JButton[1];
	String[] updName = {"����"};
	
	JButton[] inBtn = new JButton[1];
	String[] inn = {"�� ���� ��ȸ"};
	
	String[] colName = {"��ȣ","�̸�","�޴�","����","����","�����ݾ�","�Ⱓ"};
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
			table2.getTableHeader().setFont(new Font("�������", Font.BOLD, 15));
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
		
		//��ü ��ȸ
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
		
		//�Ⱓ �� ��ȸ
		proBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String st
				= JOptionPane.showInputDialog(CafeSales.this, "ó�� ��¥�� �Է��ϼ��� (YYYY/MM/DD)", "���ԱⰣ �� �˻�",JOptionPane.PLAIN_MESSAGE);
				if(st!=null) {
					String et
					= JOptionPane.showInputDialog(CafeSales.this, "������ ��¥�� �Է��ϼ��� (YYYY/MM/DD)", "���ԱⰣ �� �˻�",JOptionPane.PLAIN_MESSAGE);
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
							JOptionPane.showMessageDialog(CafeSales.this, "ã�� ����� �������� �ʽ��ϴ�");
						}
					}
				}
			}
		});
		
		//�޴��� ��ȸ
		proBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String menu
				= JOptionPane.showInputDialog(CafeSales.this, "�޴��̸��� �Է��ϼ���", "�޴��̸����� �˻�",JOptionPane.PLAIN_MESSAGE);
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
						JOptionPane.showMessageDialog(CafeSales.this, "ã�� ����� �������� �ʽ��ϴ�");
					}
				}
			}
		});
		
		//�̸����� ��ȸ
		proBtn[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name
				= JOptionPane.showInputDialog(CafeSales.this, "�̸��� �Է��ϼ���", "�̸����� �˻�",JOptionPane.PLAIN_MESSAGE);
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
						JOptionPane.showMessageDialog(CafeSales.this, "ã�� ����� �������� �ʽ��ϴ�");
					}
				}
			}
		});		
		
		// ���� ��ȸ
		inBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowCont = table2.getRowCount();
				int sum =0;
				for(int i=0;i<rowCont;i++) {
					sum += (int)table2.getValueAt(i, 5);
				}
				outTot.setText(String.valueOf(" �� �ݾ� : "+sum));
				outTot.setFont(new Font("�������", Font.BOLD, 40));
			}
		});
		
		//����
		updBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				DefaultTableModel m = (DefaultTableModel)table2.getModel();
				int num = (int)m.getValueAt(table2.getSelectedRow(), 0);
				int btn = JOptionPane.showConfirmDialog(CafeSales.this,"������ �����Ͻðڽ��ϱ�?");
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
