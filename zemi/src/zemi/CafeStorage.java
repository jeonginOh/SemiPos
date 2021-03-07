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
	String[] menu = {"전체 조회","기간 별 조회","물품 별 조회","입고","목록 수정","목록 삭제"};

	JButton[] selBtn = new JButton[4];
	String[] sel = {"제품 추가","제품 수정","제품 삭제","제품 목록"};
	
	JButton[] inBtn = new JButton[1];
	String[] inn = {"정보 조회"};
	
	JTextField outTot = new JTextField(30);
	
	String[] colName = {"순번","제품번호","물품 명","갯수","입고일"};
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
		
		//전체 조회
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
					JOptionPane.showInputDialog(CafeStorage.this, "조회 실패", "",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//이름별 조회
		menuBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "검색할 재고 이름을 입력하세요", "재고 이름으로 검색",JOptionPane.PLAIN_MESSAGE);
					
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
							JOptionPane.showMessageDialog(CafeStorage.this, "찾는 재고가 존재하지 않습니다");
						}
					}
				}catch(Exception ee) {
					JOptionPane.showInputDialog(CafeStorage.this, "조회 실패", "",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});	
		
		//기간별 조회
		menuBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String st
					= JOptionPane.showInputDialog(CafeStorage.this, "처음 날짜를 입력하세요 (YYYY/MM/DD)", "가입기간 내 검색",JOptionPane.PLAIN_MESSAGE);
					String et
					= JOptionPane.showInputDialog(CafeStorage.this, "마지막 날짜를 입력하세요 (YYYY/MM/DD)", "가입기간 내 검색",JOptionPane.PLAIN_MESSAGE);
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
								JOptionPane.showMessageDialog(CafeStorage.this, "찾는 회원이 존재하지 않습니다");
							}
						}
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		// 입고
		menuBtn[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String num
					= JOptionPane.showInputDialog(CafeStorage.this, "입고번호를 입력해주세요", "재고 추가",JOptionPane.PLAIN_MESSAGE);
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "물품이름을 입력해주세요", "재고 추가",JOptionPane.PLAIN_MESSAGE);
					String amount
					= JOptionPane.showInputDialog(CafeStorage.this, "수량을 입력해주세요", "재고 추가",JOptionPane.PLAIN_MESSAGE);
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
							JOptionPane.showMessageDialog(CafeStorage.this,"입고 완료","",JOptionPane.PLAIN_MESSAGE);
							
						}else {
							JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//입고 정보 수정
		menuBtn[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "물품 이름를 입력하세요", "입고 정보 수정",JOptionPane.YES_NO_CANCEL_OPTION);
					String amount
					= JOptionPane.showInputDialog(CafeStorage.this, "수량을 입력하세요", "입고 정보 수정",JOptionPane.YES_NO_CANCEL_OPTION);
					
					int samount = Integer.parseInt(amount);
					
					int cnum = (int)m.getValueAt(table.getSelectedRow(), 0);
					
					int btn = JOptionPane.showConfirmDialog(CafeStorage.this,"정말로 수정하시겠습니까?");
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
					JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		// 삭제
		menuBtn[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					int a = (int)m.getValueAt(table.getSelectedRow(), 0);
					int btn = JOptionPane.showConfirmDialog(CafeStorage.this,"정말로 삭제하시겠습니까?");
					if(btn==JOptionPane.YES_OPTION) {
						m.removeRow(table.getSelectedRow());
						dao.deleteS(a);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//제품 정보 조회
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
					
					outTot.setText(String.valueOf("원산지 : "+r+", 보관방법 : "+t));
					outTot.setFont(new Font("맑은고딕", Font.BOLD, 20));
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//제품 정보 추가
		selBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name
					= JOptionPane.showInputDialog(CafeStorage.this, "물품 이름을 입력하세요", "물품 등록",JOptionPane.PLAIN_MESSAGE);
					String phone
					= JOptionPane.showInputDialog(CafeStorage.this, "원산지를 입력하세요", "물품 등록",JOptionPane.PLAIN_MESSAGE);
					String howuse
					= JOptionPane.showInputDialog(CafeStorage.this, "보관방법을 입력하세요", "물품 등록",JOptionPane.YES_NO_CANCEL_OPTION);
						MarterialDTO dto = new MarterialDTO(0,name,phone,howuse);
						int n = dao1.inputM(dto);
					if(n>0) {
						JOptionPane.showMessageDialog(CafeStorage.this,"물품 등록 성공","",JOptionPane.PLAIN_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력하세요","",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
				}
			}	
		});
		//제품 정보 수정
		selBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "수정할 물품 이름를 입력하세요", "물품 정보 수정",JOptionPane.YES_NO_CANCEL_OPTION);
					String ifrom
					= JOptionPane.showInputDialog(CafeStorage.this, "원산지를 입력하세요", "물품 정보 수정",JOptionPane.YES_NO_CANCEL_OPTION);
					String howuse
					= JOptionPane.showInputDialog(CafeStorage.this, "보관방법을 입력하세요", "물품 정보 수정",JOptionPane.YES_NO_CANCEL_OPTION);
					
					int btn = JOptionPane.showConfirmDialog(CafeStorage.this,"정말로 수정하시겠습니까?");
					if(btn==JOptionPane.YES_OPTION) {
							MarterialDTO dto = new MarterialDTO(0,mname,ifrom,howuse);
							dao1.updateM(dto);
							JOptionPane.showMessageDialog(CafeStorage.this,"수정 완료","",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//제품 삭제
		selBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String mname
					= JOptionPane.showInputDialog(CafeStorage.this, "삭제할 물품을 입력하세요", "물품 정보 삭제",JOptionPane.YES_NO_CANCEL_OPTION);
					int btn = JOptionPane.showConfirmDialog(CafeStorage.this,"정말로 삭제하시겠습니까?");
					if(btn==JOptionPane.YES_OPTION) {
						m.removeRow(table.getSelectedRow());
						dao1.deleteM(mname);
					}
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(CafeStorage.this,"다시 입력해주세요","",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		//제품 목록
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
