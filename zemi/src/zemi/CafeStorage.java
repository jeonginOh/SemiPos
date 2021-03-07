package zemi;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import performance.MarterialDAO;
import performance.MarterialDTO;
import performance.SalesDTO;
import performance.StorageDAO;
import performance.StorageDTO;
import performance.UserDTO;




public class CafeStorage extends JPanel {
	JButton[] menuBtn = new JButton[6];
	String[] menu = {"��ü ��ȸ","�Ⱓ �� ��ȸ","��ǰ �� ��ȸ","�԰�","��� ����","��� ����"};

	JButton[] selBtn = new JButton[4];
	String[] sel = {"��ǰ �߰�","��ǰ ����","��ǰ ����","��ǰ ���"};
	
	JButton[] inBtn = new JButton[1];
	String[] inn = {"���� ��ȸ"};
	
	JTextField outTot = new JTextField(30);
	
	String[] colName = {"����","��ǰ��ȣ","��ǰ ��","����","�԰���"};
	String [][] data ;
	
	DefaultTableModel model = new DefaultTableModel(data,colName);
	JTable table = new JTable(model);
	
	StorageDAO dao = new StorageDAO();
	MarterialDAO dao1 = new MarterialDAO();
	
	class Board extends JPanel{
		Board(){
			setBackground(Color.WHITE);
			DefaultTableModel m = (DefaultTableModel)table.getModel();
			table.setRowHeight(50);
			table.getTableHeader().setFont(new Font("�������", Font.BOLD, 15));
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
	
	class IBtn extends JPanel{
		IBtn(){
			setBackground(Color.WHITE);
			setLayout(new GridLayout(1,4,3,3));
			
			for(int i=0;i<inn.length;i++) {
				inBtn[i]= new JButton(inn[i]);
				add(inBtn[i]);
			}
		}
	}
	
	public CafeStorage(){
		setLayout(null);
		setBackground(Color.WHITE);
		MBtn mbtn = new MBtn();
		SBtn sbtn = new SBtn();
		Board bd = new Board();
		IBtn ibtn = new IBtn();
		
		outTot.setSize(350, 70);
		outTot.setLocation(50, 480);
		add(outTot);
		
		ibtn.setSize(100, 70);
		ibtn.setLocation(400, 480);
		add(ibtn);
		
		bd.setSize(500, 500);
		bd.setLocation(25, 20);
		add(bd);
		
		mbtn.setSize(400, 430);
		mbtn.setLocation(530, 23);
		add(mbtn);
		
		sbtn.setSize(400, 70);
		sbtn.setLocation(530, 480);
		add(sbtn);
		
		//��ü ��ȸ
		menuBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for(int i=model.getRowCount()-1;i>=0;i--) {
						model.removeRow(i);
					}
					ArrayList<StorageDTO> list = dao.selectSAll();
					for(StorageDTO dto:list) {
						Vector<Object> rowData =new Vector<>();
						rowData.add(dto.getCnum());
						rowData.add(dto.getSnum());
						rowData.add(dto.getMname());
						rowData.add(dto.getSamount());
						rowData.add(dto.getInputdate());
						model.addRow(rowData);
					}
				}catch(Exception ee) {
					JOptionPane.showInputDialog(CafeStorage.this, "��ȸ ����", "",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//�̸��� ��ȸ
		menuBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "�˻��� ��� �̸��� �Է��ϼ���", "��� �̸����� �˻�",JOptionPane.PLAIN_MESSAGE);
					
					if(mname!=null) {
						ArrayList<StorageDTO> list = dao.selectS(mname);
						if(list.size()!=0) {
							
								for(int i=model.getRowCount()-1;i>=0;i--) {
									model.removeRow(i);
								}
								for(StorageDTO dto : list) {
									Vector<Object> rowData = new Vector<>();
									rowData.add(dto.getCnum());
									rowData.add(dto.getSnum());
									rowData.add(dto.getMname());
									rowData.add(dto.getSamount());
									rowData.add(dto.getInputdate());
									model.addRow(rowData);
								}
						}else{
							JOptionPane.showMessageDialog(CafeStorage.this, "ã�� ��� �������� �ʽ��ϴ�");
						}
					}
				}catch(Exception ee) {
					JOptionPane.showInputDialog(CafeStorage.this, "��ȸ ����", "",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});	
		
		//�Ⱓ�� ��ȸ
		menuBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String st
					= JOptionPane.showInputDialog(CafeStorage.this, "ó�� ��¥�� �Է��ϼ��� (YYYY/MM/DD)", "���ԱⰣ �� �˻�",JOptionPane.PLAIN_MESSAGE);
					String et
					= JOptionPane.showInputDialog(CafeStorage.this, "������ ��¥�� �Է��ϼ��� (YYYY/MM/DD)", "���ԱⰣ �� �˻�",JOptionPane.PLAIN_MESSAGE);
					if(st!=null) {
						if(et!=null) {
							for(int i=model.getRowCount()-1;i>=0;i--) {
								model.removeRow(i);
							}
							ArrayList<StorageDTO> list = dao.selectSDate(st,et);
							if(list.size()!=0) {
								for(StorageDTO dto:list) {
									Vector<Object> rowData =new Vector<>();
									rowData.add(dto.getCnum());
									rowData.add(dto.getSnum());
									rowData.add(dto.getMname());
									rowData.add(dto.getSamount());
									rowData.add(dto.getInputdate());
									model.addRow(rowData);	
								}
							}else {
								JOptionPane.showMessageDialog(CafeStorage.this, "ã�� ȸ���� �������� �ʽ��ϴ�");
							}
						}
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		// �԰�
		menuBtn[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String num
					= JOptionPane.showInputDialog(CafeStorage.this, "�԰��ȣ�� �Է����ּ���", "��� �߰�",JOptionPane.PLAIN_MESSAGE);
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "��ǰ�̸��� �Է����ּ���", "��� �߰�",JOptionPane.PLAIN_MESSAGE);
					String amount
					= JOptionPane.showInputDialog(CafeStorage.this, "������ �Է����ּ���", "��� �߰�",JOptionPane.PLAIN_MESSAGE);
					int samount = Integer.parseInt(amount);
					int snum = Integer.parseInt(num);
					if(snum!=0 & mname!=null& samount !=0) {
						StorageDTO dto = new StorageDTO(0,snum,mname,samount,null);
						for(int i=model.getRowCount()-1;i>=0;i--) {
							model.removeRow(i);
						}
						
						int n = dao.insertS(dto);
						
						Vector<Object> rowData = new Vector<>();
						rowData.add(dto.getCnum());
						rowData.add(dto.getSnum());
						rowData.add(dto.getMname());
						rowData.add(dto.getSamount());
						rowData.add(dto.getInputdate());
						
						
						model.addRow(rowData);
						if(n>0) {
							JOptionPane.showMessageDialog(CafeStorage.this,"�԰� �Ϸ�","",JOptionPane.PLAIN_MESSAGE);
							
						}else {
							JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//�԰� ���� ����
		menuBtn[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "��ǰ �̸��� �Է��ϼ���", "�԰� ���� ����",JOptionPane.YES_NO_CANCEL_OPTION);
					String amount
					= JOptionPane.showInputDialog(CafeStorage.this, "������ �Է��ϼ���", "�԰� ���� ����",JOptionPane.YES_NO_CANCEL_OPTION);
					
					int samount = Integer.parseInt(amount);
					
					int cnum = (int)m.getValueAt(table.getSelectedRow(), 0);
					
					int btn = JOptionPane.showConfirmDialog(CafeStorage.this,"������ �����Ͻðڽ��ϱ�?");
					if(btn==JOptionPane.YES_OPTION) {
							
							StorageDTO dto = new StorageDTO(cnum,0,mname,samount,null);
							dao.update(dto);
							
							for(int i=model.getRowCount()-1;i>=0;i--) {
								model.removeRow(i);
							}
							Vector<Object> rowData = new Vector<>();
							rowData.add(dto.getCnum());
							rowData.add(dto.getSnum());
							rowData.add(dto.getMname());
							rowData.add(dto.getSamount());
							rowData.add(dto.getInputdate());
							model.addRow(rowData);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		// ����
		menuBtn[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					int a = (int)m.getValueAt(table.getSelectedRow(), 0);
					int btn = JOptionPane.showConfirmDialog(CafeStorage.this,"������ �����Ͻðڽ��ϱ�?");
					if(btn==JOptionPane.YES_OPTION) {
						m.removeRow(table.getSelectedRow());
						dao.deleteS(a);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//��ǰ ���� ��ȸ
		inBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String a = (String)m.getValueAt(table.getSelectedRow(), 2);
					
					MarterialDTO dto1 = dao1.selectM(a);
					String q = dto1.getMname();
					String r = dto1.getIfrom();
					String t = dto1.getHowuse();
					
					outTot.setText(String.valueOf("������ : "+r+", ������� : "+t));
					outTot.setFont(new Font("�������", Font.BOLD, 20));
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//��ǰ ���� �߰�
		selBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name
					= JOptionPane.showInputDialog(CafeStorage.this, "��ǰ �̸��� �Է��ϼ���", "��ǰ ���",JOptionPane.PLAIN_MESSAGE);
					String phone
					= JOptionPane.showInputDialog(CafeStorage.this, "�������� �Է��ϼ���", "��ǰ ���",JOptionPane.PLAIN_MESSAGE);
					String howuse
					= JOptionPane.showInputDialog(CafeStorage.this, "��������� �Է��ϼ���", "��ǰ ���",JOptionPane.YES_NO_CANCEL_OPTION);
						MarterialDTO dto = new MarterialDTO(0,name,phone,howuse);
						int n = dao1.inputM(dto);
					if(n>0) {
						JOptionPane.showMessageDialog(CafeStorage.this,"��ǰ ��� ����","",JOptionPane.PLAIN_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է��ϼ���","",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
				}
			}	
		});
		//��ǰ ���� ����
		selBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "������ ��ǰ �̸��� �Է��ϼ���", "��ǰ ���� ����",JOptionPane.YES_NO_CANCEL_OPTION);
					String ifrom
					= JOptionPane.showInputDialog(CafeStorage.this, "�������� �Է��ϼ���", "��ǰ ���� ����",JOptionPane.YES_NO_CANCEL_OPTION);
					String howuse
					= JOptionPane.showInputDialog(CafeStorage.this, "��������� �Է��ϼ���", "��ǰ ���� ����",JOptionPane.YES_NO_CANCEL_OPTION);
					
					int btn = JOptionPane.showConfirmDialog(CafeStorage.this,"������ �����Ͻðڽ��ϱ�?");
					if(btn==JOptionPane.YES_OPTION) {
							MarterialDTO dto = new MarterialDTO(0,mname,ifrom,howuse);
							dao1.updateM(dto);
							JOptionPane.showMessageDialog(CafeStorage.this,"���� �Ϸ�","",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//��ǰ ����
		selBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "������ ��ǰ�� �Է��ϼ���", "��ǰ ���� ����",JOptionPane.YES_NO_CANCEL_OPTION);
					int btn = JOptionPane.showConfirmDialog(CafeStorage.this,"������ �����Ͻðڽ��ϱ�?");
					if(btn==JOptionPane.YES_OPTION) {
						m.removeRow(table.getSelectedRow());
						dao1.deleteM(mname);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"�ٽ� �Է����ּ���","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//��ǰ ���
		selBtn[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=model.getRowCount()-1;i>=0;i--) {
					model.removeRow(i);
				}
				ArrayList<MarterialDTO> list = dao1.selectAll();
				for(MarterialDTO dto:list) {
					Vector<Object> rowData =new Vector<>();
					rowData.add(dto.getInum());
					rowData.add(dto.getMname());
					rowData.add(dto.getIfrom());
					rowData.add(dto.getHowuse());
					model.addRow(rowData);
				}
			}
		});
	}
}
