package performance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import zemi.CafeConnect;

public class MarterialDAO {
	
	//조회
	public MarterialDTO selectM(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM MARTERIAL WHERE MNAME = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int inum = rs.getInt("INUM");
				String mname = rs.getString("MNAME");
				String ifrom = rs.getString("IFROM");
				String howuse = rs.getString("HOWUSE");
				MarterialDTO dto = new MarterialDTO(inum,mname,ifrom,howuse);
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
	
	//추가
	public int inputM(MarterialDTO inp) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql = "INSERT INTO MARTERIAL VALUES(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1,inp.getInum());
			pstmt.setString(2, inp.getMname());
			pstmt.setString(3, inp.getIfrom());
			pstmt.setString(4,inp.getHowuse());
			int n = pstmt.executeUpdate();
			
			return n;
		}catch(SQLException se) {
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally{
			CafeConnect.close(con, pstmt, null);
		}
	}
	
	//수정
	public int updateM(MarterialDTO inp) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			="UPDATE MARTERIAL SET IFROM = ?, HOWUSE = ? WHERE MNAME = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,inp.getIfrom());
			pstmt.setString(2,inp.getHowuse());
			pstmt.setString(3, inp.getMname());
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
	//삭제
	public int deleteM(String mname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql = "DELETE FROM MARTERIAL WHERE MNAME = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mname);
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
	public ArrayList<MarterialDTO> selectAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT *FROM MARTERIAL";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<MarterialDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				int inum = rs.getInt("INUM");
				String mname = rs.getString("MNAME");
				String ifrom = rs.getString("IFROM");
				String howuse = rs.getString("HOWUSE");
				
				MarterialDTO dto = new MarterialDTO(inum,mname,ifrom,howuse);
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
}
