package performance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import zemi.CafeConnect;

public class StorageDAO {
	
	//입고
	public int insertS(StorageDTO st) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			="INSERT INTO STORAGE VALUES(STORAGE_SEQ.NEXTVAL,?,?,?,SYSDATE)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, st.getSnum());
			pstmt.setString(2, st.getMname());
			pstmt.setInt(3, st.getSamount());
			int n = pstmt.executeUpdate();
			return n;
		}catch(SQLException e) {
			return -1;
		}catch(Exception se) {
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
	}
	
	//전체조회
	public ArrayList<StorageDTO> selectSAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT *FROM STORAGE";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<StorageDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				int cnum = rs.getInt("CNUM");
				int snum = rs.getInt("SNUM");
				String mname = rs.getString("MNAME");
				int samount = rs.getInt("SAMOUNT");
				Date regdate = rs.getDate("INPUTDATE");
				
				StorageDTO dto = new StorageDTO(cnum,snum,mname,samount,regdate);
				list.add(dto);
			}
			return list;
		}catch(SQLException ee) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
	
	//물품별 조회
	public ArrayList<StorageDTO> selectS(String mname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM STORAGE WHERE MNAME LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+mname+"%");
			rs = pstmt.executeQuery();
			ArrayList<StorageDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				int cnum = rs.getInt("CNUM");
				int snum = rs.getInt("SNUM");
				String name = rs.getString("MNAME");
				int samount = rs.getInt("SAMOUNT");
				Date regdate = rs.getDate("INPUTDATE");
				
				StorageDTO dto = new StorageDTO(cnum,snum,name,samount,regdate);
				list.add(dto);
			}
				return list;
		}catch(SQLException ee) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
	//기간 별 조회
	public ArrayList<StorageDTO> selectSDate(String st,String et){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM STORAGE WHERE INPUTDATE BETWEEN ? AND ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, st);
			pstmt.setString(2, et);
			rs = pstmt.executeQuery();
			ArrayList<StorageDTO> list = new ArrayList<>();
			while(rs.next()) {
				int cnum = rs.getInt("CNUM");
				int snum = rs.getInt("PRICE");
				String mname = rs.getString("AMOUNT");
				int samount = rs.getInt("SAMOUNT");
				Date inputdate = rs.getDate("INPUTDATE");
				
				StorageDTO dto = new StorageDTO(cnum,snum,mname,samount,inputdate);
				list.add(dto);
			}
				return list;
		}catch(SQLException ee) {
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			CafeConnect.close(con, pstmt, rs);
		}
	}
	
	//삭제
	public int deleteS(int a) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "DELETE FROM STORAGE WHERE SNUM = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,a);
			int n = pstmt.executeUpdate();
			return n;
		}catch(SQLException ee) {
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}	
	}
	
	//수정
	public int update(StorageDTO st) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "UPDATE STORAGE SET MNAME = ?, SAMOUNT = ?"+
			"WHERE SNUM = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,st.getMname());
			pstmt.setInt(2,st.getSamount());
			int n = pstmt.executeUpdate();
			return n;
		}catch(SQLException ee) {
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
	}
	
	//null값 치환
	public void nullChange() {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "UPDATE STORAGE SET MNAME = '폐기된 물품' WHERE NAME IS NULL";
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
