package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;
import dto.ItemDTO;

public class ItemDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public ArrayList<ItemDTO> getItemList(String type, String userId) {
		ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
		
		System.out.println("아이템 타입 : " + type + " + 요청 사용자 :" + userId);
		String sql = "SELECT ITEM_NAME, UNIT_PRICE FROM ITEMS WHERE ITEM_TYPE = ? AND MNAME_ID = ?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ItemDTO item = new ItemDTO();
				item.setItemName(rs.getString(1));
				item.setUnitPrice(rs.getInt(2));
				
				System.out.println(item.getItemName() + " , " + item.getUnitPrice());
				list.add(item);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
}
