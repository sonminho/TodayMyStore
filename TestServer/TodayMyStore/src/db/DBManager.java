package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

public class DBManager {
	
	// �����ͺ��̽� ���� ��������
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			String dbURL = "jdbc:mysql://localhost:3306/tms";
			String dbId = "TMS";
			String dbPw = "zerock";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbId, dbPw);
			System.out.println("��� ���ټ���");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// ���� ���ҽ� ���� (Select ��)
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// ���� ���ҽ� ���� (Insert, Update, Delete ��)
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			pstmt.close();
			conn.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}