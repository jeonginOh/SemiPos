package zemi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CafeConnect {
	public static Connection getConn() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.0.12:1521:xe";
			con = DriverManager.getConnection(url,"scott","tiger");
		}catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}catch(SQLException se) {
			se.printStackTrace();
		}
		return con;
	}
	
	public void close(Connection con) {
		try {
			if(con!=null) con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public void close(Statement stmt) {
		try {
			if(stmt!=null) stmt.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public void close(ResultSet rs) {
		try {
			if(rs!=null) rs.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(con!=null) con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
}

