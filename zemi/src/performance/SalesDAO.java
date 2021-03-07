package performance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import zemi.CafeConnect;

public class SalesDAO {
	
	//추가
	public int insert(SalesDTO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql 
			= "INSERT INTO SALESLIST VALUES(SALESLIST_SEQ.NEXTVAL,?,?,?,?,?,SYSDATE)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem.getName());
			pstmt.setString(2,mem.getMenu());
			pstmt.setInt(3,mem.getPrice());
			pstmt.setInt(4,mem.getAmount() );
			pstmt.setInt(5,mem.getTot());
			int n = pstmt.executeUpdate();
			return n;
		}catch(SQLException se) {
			se.printStackTrace();
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
	}
	
	//삭제
	public int delete(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "DELETE FROM SALESLIST WHERE NUM = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			int n = pstmt.executeUpdate();
			return n;
		}catch(SQLException se) {
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
		
	}
	
	//수정
	public int update(SalesDTO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "UPDATE SALESLIST SET NAME = ?, MENU = ?, PRICE = ?, AMOUNT = ?"+
			"WHERE NUM = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,mem.getName());
			pstmt.setString(2,mem.getMenu());
			pstmt.setInt(3,mem.getPrice());
			pstmt.setInt(4,mem.getAmount());
			pstmt.setInt(5,mem.getNum());
			int n = pstmt.executeUpdate();
			return n;
		}catch(SQLException se) {
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
	}
	
	//전체조회
	public ArrayList<SalesDTO> selectAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT *FROM SALESLIST";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<SalesDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				int num = rs.getInt("NUM");
				String name = rs.getString("NAME");
				String menu = rs.getString("MENU");
				int price = rs.getInt("PRICE");
				int amount = rs.getInt("AMOUNT");
				int tot = price*amount;
				Date regdate = rs.getDate("REGDATE");
				
				SalesDTO dto = new SalesDTO(num,name,menu,price,amount,tot,regdate);
				list.add(dto);
			}
			return list;
		}catch(SQLException se) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
	
	// 이름으로 조회
	public ArrayList<SalesDTO> selectName(String name){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM SALESLIST WHERE NAME LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,"%"+name+"%");
			rs = pstmt.executeQuery();
			ArrayList<SalesDTO> list = new ArrayList<>();
			while(rs.next()) {
				int num = rs.getInt("NUM");
				String menu = rs.getString("MENU");
				int price = rs.getInt("PRICE");
				int amount = rs.getInt("AMOUNT");
				int tot = price*amount;
				Date regdate = rs.getDate("REGDATE");
				SalesDTO dto = new SalesDTO(num,name,menu,price,amount,tot,regdate);
				list.add(dto);
			}
			return list;
		}catch(SQLException se) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
		
	// 메뉴 이름으로 조회
	public ArrayList<SalesDTO> selectMenu(String menu){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM SALESLIST WHERE MENU LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+menu+"%");
			rs = pstmt.executeQuery();
			ArrayList<SalesDTO> list = new ArrayList<>();
			while(rs.next()) {
				int num = rs.getInt("NUM");
				String name = rs.getString("NAME");
				int price = rs.getInt("PRICE");
				int amount = rs.getInt("AMOUNT");
				int tot = price*amount;
				Date regdate = rs.getDate("REGDATE");
				SalesDTO dto = new SalesDTO(num,name,menu,price,amount,tot,regdate);
				list.add(dto);
			}
			return list;
		}catch(SQLException se) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
	
	//기간 범위 내 조회
	public ArrayList<SalesDTO> selectDate(String st,String et){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM SALESLIST WHERE REGDATE BETWEEN ? AND ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, st);
			pstmt.setString(2, et);
			rs = pstmt.executeQuery();
			ArrayList<SalesDTO> list = new ArrayList<>();
			while(rs.next()) {
				int num = rs.getInt("NUM");
				String name = rs.getString("NAME");
				String menu = rs.getString("MENU");
				int price = rs.getInt("PRICE");
				int amount = rs.getInt("AMOUNT");
				int tot = price*amount;
				Date regdate = rs.getDate("REGDATE");
				SalesDTO dto = new SalesDTO(num,name,menu,price,amount,tot,regdate);
				
				list.add(dto);
			}
			return list;
		}catch(SQLException se) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
	
	//null값 치환
	public void nullChange() {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "UPDATE SALESLIST SET NAME = '비회원' WHERE NAME IS NULL";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
	}
	
}
