package zemi;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import performance.UserDAO;
import performance.UserDTO;


public class CafeUser extends JPanel {
	
	JButton[] updBtn = new JButton[3];
	String[] updName = {"ȸ������","����","����"};
	
	String[] colName = {"�̸�","��ȭ��ȣ","����Ʈ","������"};
	String[][] data;
	DefaultTableModel model2 = new DefaultTableModel(data,colName);
	JTable table2 = new JTable(model2);
	
	JButton[] selBtn = new JButton[4];
	String[] sel = {"��üȸ�� ��ȸ","�̸����� ã��", "��ȭ��ȣ�� ã��", "�Ⱓ �� ��ȸ"};
	
	UserDAO dao = new UserDAO();
	
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
	
	class SBtn extends JPanel{
		SBtn(){
			setBackground(Color.WHITE);
			setLayout(new GridLayout(4,3,3,3));
			
			for(int i=0;i<sel.length;i++) {
				selBtn[i]= new JButton(sel[i]);
				add(selBtn[i]);
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
	public CafeUser() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		Board2 bd2 = new Board2();
		UBtn ubtn = new UBtn();
		SBtn sbtn = new SBtn();
		
		bd2.setSize(500,600);
		bd2.setLocation(25,20);
		add(bd2);
		
		sbtn.setSize(400,430);
		sbtn.setLocation(530,23);
		add(sbtn);
		
		ubtn.setSize(400,70);
		ubtn.setLocation(530,480);
		add(ubtn);
		
		//��ü ��ȸ
		selBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=model2.getRowCount()-1;i>=0;i--) {
					model2.removeRow(i);
				}
				ArrayList<UserDTO> list = dao.selectUser();
				for(UserDTO dto:list) {
					Vector<Object> rowData =new Vector<>();
					rowData.add(dto.getName());
					rowData.add(dto.getPhone());
					rowData.add(dto.getPoint());
					rowData.add(dto.getIndate());
					model2.addRow(rowData);
				}
			}
		});
		
		//�̸����� ��ȸ
		selBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name
				= JOptionPane.showInputDialog(CafeUser.this, "�˻��� ȸ�� �̸��� �Է��ϼ���", "ȸ�� �̸����� �˻�",JOptionPane.PLAIN_MESSAGE);
				if(name!=null) {
					UserDTO dto = dao.selectUserName(name);
					if(dto!=null) {
							for(int i=model2.getRowCount()-1;i>=0;i--) {
								model2.removeRow(i);
							}
							Vector<Object> rowData = new Vector<>();
							rowData.add(dto.getName());
							rowData.add(dto.getPhone());
							rowData.add(dto.getPoint());
							rowData.add(dto.getIndate());
							model2.addRow(rowData);
					}else{
						JOptionPane.showMessageDialog(CafeUser.this, "ã�� ȸ���� �������� �ʽ��ϴ�");
					}
				}
			}
		});
		
		//��ȭ��ȣ 4�ڸ��� ��ȸ
		selBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String phone
				= JOptionPane.showInputDialog(CafeUser.this, "�˻��� ȸ���� ��ȭ��ȣ�� �Է��ϼ���", "ȸ�� ��ȭ ��ȣ�� �˻�",JOptionPane.PLAIN_MESSAGE);
				ArrayList<UserDTO> list = dao.selectUserPhone(phone);
				if(phone!=null) {
					if(list.size()!=0) {
						for(int i=model2.getRowCount()-1;i>=0;i--) {
							model2.removeRow(i);
						}
						for(UserDTO dto:list) {
							Vector<Object> rowData =new Vector<>();
							rowData.add(dto.getName());
							rowData.add(dto.getPhone());
							rowData.add(dto.getPoint());
							rowData.add(dto.getIndate());
							model2.addRow(rowData);	
						}
					}else{
						JOptionPane.showMessageDialog(CafeUser.this, "ã�� ȸ���� �������� �ʽ��ϴ�");
					}
				}
			}
		});
		
		//�Ⱓ �� ��ȸ
		selBtn[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String st
				= JOptionPane.showInputDialog(CafeUser.this, "ó�� ��¥�� �Է��ϼ��� (YYYY/MM/DD)", "���ԱⰣ �� �˻�",JOptionPane.PLAIN_MESSAGE);
				if(st!=null) {
					String et
					= JOptionPane.showInputDialog(CafeUser.this, "������ ��¥�� �Է��ϼ��� (YYYY/MM/DD)", "���ԱⰣ �� �˻�",JOptionPane.PLAIN_MESSAGE);
					if(et!=null) {
						for(int i=model2.getRowCount()-1;i>=0;i--) {
							model2.removeRow(i);
						}
						ArrayList<UserDTO> list = dao.selectUserDate(st,et);
						if(list.size()!=0) {
							for(UserDTO dto:list) {
								Vector<Object> rowData =new Vector<>();
								rowData.add(dto.getName());
								rowData.add(dto.getPhone());
								rowData.add(dto.getPoint());
								rowData.add(dto.getIndate());
								model2.addRow(rowData);	
							}
						}else {
							JOptionPane.showMessageDialog(CafeUser.this, "ã�� ȸ���� �������� �ʽ��ϴ�");
						}
					}
				}
			}
		});
		
		//ȸ�� ����
		updBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name
				= JOptionPane.showInputDialog(CafeUser.this, "�̸��� �Է��ϼ���", "ȸ������",JOptionPane.PLAIN_MESSAGE);
				if(name!=null) {
					String phone
					= JOptionPane.showInputDialog(CafeUser.this, "��ȭ��ȣ�� �Է��ϼ���", "ȸ������",JOptionPane.PLAIN_MESSAGE);
					if(phone!=null) {
						UserDTO dto = new UserDTO(name,phone,0,null);
						for(int i=model2.getRowCount()-1;i>=0;i--) {
							model2.removeRow(i);
						}
						
						int n = dao.insert(dto);
						UserDTO dto1 = dao.selectUserName(name);
						
						Vector<Object> rowData = new Vector<>();
						rowData.add(dto1.getName());
						rowData.add(dto1.getPhone());
						rowData.add(dto1.getPoint());
						rowData.add(dto1.getIndate());
						
						
						model2.addRow(rowData);
						if(n>0) {
							JOptionPane.showMessageDialog(CafeUser.this,"ȸ�����Լ���!","",JOptionPane.PLAIN_MESSAGE);
							
						}else {
							JOptionPane.showMessageDialog(CafeUser.this,"ȸ�����Խ���!","",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			}
		});
		
		//ȸ�� ����
		updBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel m = (DefaultTableModel)table2.getModel();
				String phone
				= JOptionPane.showInputDialog(CafeUser.this, "��ȭ��ȣ�� �Է��ϼ���", "ȸ�� ���� ����",JOptionPane.YES_NO_CANCEL_OPTION);
				String point
				= JOptionPane.showInputDialog(CafeUser.this, "����Ʈ�� �Է��ϼ���", "ȸ�� ���� ����",JOptionPane.YES_NO_CANCEL_OPTION);
				
				int po = Integer.parseInt(point);
				
				String name = (String)m.getValueAt(table2.getSelectedRow(), 0);
				
				int btn = JOptionPane.showConfirmDialog(CafeUser.this,"������ �����Ͻðڽ��ϱ�?");
				if(btn==JOptionPane.YES_OPTION) {
						
					UserDTO dto = new UserDTO(name,phone,po,null);
					dao.update(dto);
					UserDTO dto1 = dao.selectUserName(name);
					for(int i=model2.getRowCount()-1;i>=0;i--) {
						model2.removeRow(i);
					}
					Vector<Object> rowData = new Vector<>();
					rowData.add(dto1.getName());
					rowData.add(dto1.getPhone());
					rowData.add(dto1.getPoint());
					rowData.add(dto1.getIndate());
					model2.addRow(rowData);
				}
			}
		});
		
		//ȸ�� ����
		updBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel m = (DefaultTableModel)table2.getModel();
				String a = (String)m.getValueAt(table2.getSelectedRow(), 0);
				int btn = JOptionPane.showConfirmDialog(CafeUser.this,"������ �����Ͻðڽ��ϱ�?");
				if(btn==JOptionPane.YES_OPTION) {
					m.removeRow(table2.getSelectedRow());
					dao.delete(a);
				}
			}
		});
	}
}
