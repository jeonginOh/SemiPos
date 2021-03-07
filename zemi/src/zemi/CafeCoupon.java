package zemi;

import java.awt.Color;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import performance.CouponDTO;
import performance.CouponDAO;

public class CafeCoupon extends JPanel {
	JButton[] menuBtn = new JButton[3];
	String[] menu = {"쿠폰 조회","쿠폰 등록","선택 삭제"};
	

	String[] colName = {"쿠폰 번호","쿠폰 내용","할인가격","유효기간"};
	String [][] data ;

	DefaultTableModel model = new DefaultTableModel(data,colName);
	JTable table = new JTable(model);
	
	Date n = null;
	
	CouponDAO dao = new CouponDAO();
	
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
			setLayout(new GridLayout(3,3,3,3));
			setBackground(Color.WHITE);
			for(int i=0;i<menuBtn.length;i++) {
				menuBtn[i]= new JButton(menu[i]);
				add(menuBtn[i]);
			}
		}
	}
	
	public CafeCoupon(){
		setLayout(null);
		setBackground(Color.WHITE);
		MBtn mbtn = new MBtn();

		Board bd = new Board();
		
		bd.setSize(500, 500);
		bd.setLocation(25, 20);
		add(bd);
		
		mbtn.setSize(400, 430);
		mbtn.setLocation(530, 23);
		add(mbtn);
		
		//쿠폰 전체 조회
		menuBtn[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=model.getRowCount()-1;i>=0;i--) {
					model.removeRow(i);
				}
				ArrayList<CouponDTO> list = dao.selectAll();
				for(CouponDTO dto:list) {
					Vector<Object> rowData =new Vector<>();
					rowData.add(dto.getCounum());
					rowData.add(dto.getCouval());
					rowData.add(dto.getSaleprice());
					rowData.add(dto.getLastdate());
					model.addRow(rowData);
				}
			}
		});
		
		//쿠폰 등록
		menuBtn[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String counum
				= JOptionPane.showInputDialog(CafeCoupon.this, "쿠폰 번호을 입력하세요", "쿠폰 등록",JOptionPane.PLAIN_MESSAGE);
					if(counum!=null) {
					String couval
					= JOptionPane.showInputDialog(CafeCoupon.this, "쿠폰 내용을 입력하세요", "쿠폰 등록",JOptionPane.PLAIN_MESSAGE);
						if(couval!=null) {
						String saleprice
						= JOptionPane.showInputDialog(CafeCoupon.this, "할인 가격을 입력하세요", "쿠폰 등록",JOptionPane.PLAIN_MESSAGE);
							if(saleprice!=null) {
							String days
							= JOptionPane.showInputDialog(CafeCoupon.this, "유효 기간을 입력하세요(--일)", "쿠폰 등록",JOptionPane.PLAIN_MESSAGE);
								if(days!=null) {
									int sa = Integer.parseInt(saleprice);
									int da = Integer.parseInt(days);
									CouponDTO dto = new CouponDTO(counum,couval,sa,n,da);
									for(int i=model.getRowCount()-1;i>=0;i--) {
										model.removeRow(i);
									}
									int n = dao.insertCoupon(dto);
									Vector<Object> rowData = new Vector<>();
									rowData.add(counum);
									rowData.add(couval);
									rowData.add(saleprice);
									rowData.add("오늘부터"+days+"일");
									
									model.addRow(rowData);
									
								}else{
									JOptionPane.showMessageDialog(CafeCoupon.this,"다시 입력하세요");
								}
							}
						}
					}
				}
			});
		
		//쿠폰 삭제
		menuBtn[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel m = (DefaultTableModel)table.getModel();
					String a = (String)m.getValueAt(table.getSelectedRow(), 0);
					int btn = JOptionPane.showConfirmDialog(CafeCoupon.this,"정말로 삭제하시겠습니까?");
					if(btn==JOptionPane.YES_OPTION) {
						m.removeRow(table.getSelectedRow());
						dao.deleteCoupon(a);
					}
				}catch(ArrayIndexOutOfBoundsException ae) {
					
				}
			}
		});
	}
}
