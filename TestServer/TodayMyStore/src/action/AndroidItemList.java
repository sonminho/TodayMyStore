package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ItemDAO;
import dto.ItemDTO;

public class AndroidItemList implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Gson gson = new Gson();
		
		ItemDTO item = gson.fromJson(request.getParameter("item"), ItemDTO.class);
		
		ItemDAO iDao = new ItemDAO();
		ArrayList<ItemDTO> list = iDao.getItemList(item.getItemType(), item.getUserId());
		
		StringBuilder sb = new StringBuilder();
		
		int listSize = list.size();
		
		for(int i = 0; i < listSize; i++) {
			System.out.println("아이템 리스트" + list.get(i).getUserId());
		}
		
		System.out.println(gson.toJson(list));
		
		response.getWriter().append(gson.toJson(list));
	}

}