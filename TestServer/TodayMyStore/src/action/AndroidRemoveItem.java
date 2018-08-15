package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.ItemDAO;
import dto.ItemDTO;

public class AndroidRemoveItem implements Action{ // 아이템 삭제 

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		ArrayList<ItemDTO> list = gson.fromJson(request.getParameter("list"), new TypeToken<ArrayList<ItemDTO>>(){}.getType()); // 클라이언트로부터 아이템 JSON 배열 객체를 받음
		
		ItemDAO iDao = new ItemDAO();
		ItemDTO item = gson.fromJson(request.getParameter("item"), ItemDTO.class);
		
			
		System.out.println("아이템 타입 " + item.getItemType() + ", 사용자 아이디" + item.getUserId());
		
		int listSize = list.size();
		int result = -1;
		
		for(int i = 0 ; i < listSize; i++) { // 리스트의 아이템 항목만큼 검색해서 항목 삭제
			System.out.println("삭제될 항목 " + list.get(i).getItemName() + "아이템 타입 " + item.getItemType() + "사용자 " + item.getUserId());
			result = iDao.removeItem(item.getItemType(), list.get(i).getItemName(), item.getUserId());
		}
		
		if(result > 0) {
			response.getWriter().append("1");
		} else {
			response.getWriter().append("0");
		}
	}
	
}
