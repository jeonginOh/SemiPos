package performance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import zemi.CafeConnect;

public class UserDAO {
	//회원 가입
	public int insert(UserDTO user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "INSERT INTO USERLIST VALUES(?,?,?,SYSDATE)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPhone());
			pstmt.setInt(3,user.getPoint());
			int n = pstmt.executeUpdate();
			
			return n;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
	}
	//회원 정보수정
	public int update(UserDTO user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con=CafeConnect.getConn();
			String sql
			= "UPDATE USERLIST SET PHONE = ?,POINT = ? WHERE NAME = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(3,user.getName());
			pstmt.setString(1,user.getPhone());
			pstmt.setInt(2,user.getPoint());
			int n = pstmt.executeUpdate();
			return n;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
		
	}
	//전체 회원 조회
	public ArrayList<UserDTO> selectUser(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM USERLIST";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<UserDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				String name = rs.getString("NAME");
				String phone = rs.getString("PHONE");
				int point = rs.getInt("POINT");
				Date indate = rs.getDate("INDATE");
				
				UserDTO dto = new UserDTO(name,phone,point,indate);
				list.add(dto);
			}
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
	//이름으로 찾기
	public UserDTO selectUserName(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM USERLIST WHERE NAME = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String phone = rs.getString("PHONE");
				int point = rs.getInt("POINT");
				Date indate = rs.getDate("INDATE");
				UserDTO dto = new UserDTO(name,phone,point,indate);
				return dto;
			}else {
				return null;
			}
		}catch(SQLException se) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
	//전화번호로 찾기
	public ArrayList<UserDTO> selectUserPhone(String phone) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM USERLIST WHERE PHONE LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+phone+"%");
			rs = pstmt.executeQuery();
			ArrayList<UserDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				String name = rs.getString("NAME");
				int point = rs.getInt("POINT");
				String phone1 = rs.getString("PHONE");
				Date indate = rs.getDate("INDATE");
				UserDTO dto = new UserDTO(name,phone1,point,indate);
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
	
	//가입일 범위내 조회
	public ArrayList<UserDTO> selectUserDate(String st,String et) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM USERLIST WHERE INDATE BETWEEN ? AND ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1,st);
			 pstmt.setString(2, et);
			 rs = pstmt.executeQuery();
			 ArrayList<UserDTO> list = new ArrayList<>();
			 while(rs.next()) {
				 String name = rs.getString("NAME");
				 String phone = rs.getString("PHONE");
				 int point = rs.getInt("POINT");
				 Date indate = rs.getDate("INDATE");
				 UserDTO dto = new UserDTO(name,phone,point,indate);
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
	
	public int delete(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql = "DELETE FROM USERLIST WHERE NAME = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,name);
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
	

	//포인트 적립
		public int point(UserDTO dto,double po) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con=CafeConnect.getConn();
				String sql
				= "UPDATE USERLIST SET POINT = point+? WHERE NAME = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(2,dto.getName());
				pstmt.setDouble(1,po);
				int n = pstmt.executeUpdate();
				return n;
			}catch(Exception e) {
				e.printStackTrace();
				return -1;
			}finally {
				CafeConnect.close(con, pstmt, null);
			}
			
		}
}
