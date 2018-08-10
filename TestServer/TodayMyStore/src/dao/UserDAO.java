package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.DBManager;
import dto.UserDTO;

public class UserDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// ����� ���� ��ȯ
	public UserDTO userInfo(String mId) {
		String sql = "SELECT MNAME, MEMAIL, MPHONE FROM USERS WHERE MID = ?";
		UserDTO user = new UserDTO();
		System.out.println("DAO " + mId);
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user.setmName(rs.getString(1));
				user.setmEmail(rs.getString(2));
				user.setmPhone(rs.getString(3));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return user;
	}
	
	// ����� ������ ��ȣ�� ��ġ�ϴ��� Ȯ��
	public int userCheck(String mId, String mPw) {
		int result = -1;
		String sql = "SELECT * FROM users WHERE mid = ? and mpw =?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mId);
			pstmt.setString(2, mPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = 1;
			} else { 
				result = -1;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return result;
	}
	
	// ����� ������ �����ϴ��� Ȯ��
	public int userIdCheck(String mId) {
		int result = -1;
		String sql = "SELECT * FROM users WHERE mId = ?";

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = 1;
			} else { 
				result = -1;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	// ����� �߰�
	public int userJoin(String mId, String mPw, String mName, String mEmail, String mPhone) {
		int result = -1;
		String sql = "INSERT INTO USERS (MID, MPW, MNAME, MEMAIL, MPHONE) VALUES (?, ?, ?, ?, ?)";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mId);
			pstmt.setString(2, mPw);
			pstmt.setString(3, mName);
			pstmt.setString(4, mEmail);
			pstmt.setString(5, mPhone);
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return result;
	}
	
	// ����� ���� ������Ʈ
	public int userUpdate(String mId, String mPw, String mName, String mEmail, String mPhone) {
		int result = -1;
		String sql = "UPDATE USERS SET mPw = ?, mName = ?, mEmail = ?, mPhone = ?, UPDATE_DATE = ?, UPDATE_BY = ? WHERE mId = ?";
		
		Date dateNow = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = sdf.format(dateNow);
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mPw);
			pstmt.setString(2, mName);
			pstmt.setString(3, mEmail);
			pstmt.setString(4, mPhone);
			pstmt.setString(5, now);
			pstmt.setString(6, mId);
			pstmt.setString(7, mId);
			
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return result;
	}
}