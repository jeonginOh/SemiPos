package performance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import zemi.CafeConnect;

public class CouponDAO {
	
	//ÄíÆù Á¶È¸
	public CouponDTO selectC(String counum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT * FROM COUPON WHERE COUNUM = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, counum);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String couval = rs.getString("COUVAL");
				int saleprice = rs.getInt("SALEPRICE");
				Date lastdate = rs.getDate("LASTDATE");
				CouponDTO dto = new CouponDTO(counum,couval,saleprice,lastdate,0);
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
	
	//ÀüÃ¼Á¶È¸
	public ArrayList<CouponDTO> selectAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = CafeConnect.getConn();
			String sql = "SELECT *FROM COUPON";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<CouponDTO> list = new ArrayList<>();
			
			while(rs.next()) {
				String counum = rs.getString("COUNUM");
				String couval = rs.getString("COUVAL");
				int saleprice = rs.getInt("SALEPRICE");
				Date lastdate = rs.getDate("LASTDATE");

				CouponDTO dto = new CouponDTO(counum,couval,saleprice,lastdate,0);
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
	
	//ÄíÆù µî·Ï
	public int insertCoupon(CouponDTO cou) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			="INSERT INTO COUPON VALUES(?,?,?,SYSDATE+?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,cou.getCounum());
			pstmt.setString(2, cou.getCouval());
			pstmt.setInt(3,cou.getSaleprice());
			pstmt.setInt(4,cou.getDays());
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
	//ÄíÆù »èÁ¦
	public int deleteCoupon(String counum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "DELETE FROM COUPON WHERE COUNUM = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, counum);
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
	//ÄíÆù ¼öÁ¤
	public int updateCoupon(CouponDTO cou) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = CafeConnect.getConn();
			String sql
			= "UPDATE COUPON SET COUVAL = ?, SALESPRICE = ?, LASTDATE = SYSDATE+?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cou.getCouval());
			pstmt.setInt(2, cou.getSaleprice());
			pstmt.setInt(3,cou.getDays());
			return 1;
		}catch(SQLException se) {
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			CafeConnect.close(con, pstmt, null);
		}
	}
}
