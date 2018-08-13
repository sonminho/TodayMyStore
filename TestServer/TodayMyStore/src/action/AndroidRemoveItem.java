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

public class AndroidRemoveItem implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		ArrayList<ItemDTO> list = gson.fromJson(request.getParameter("list"), new TypeToken<ArrayList<ItemDTO>>(){}.getType());
		
		ItemDAO iDao = new ItemDAO();
		ItemDTO item = gson.fromJson(request.getParameter("item"), ItemDTO.class);
		
			
		System.out.println("������ Ÿ�� " + item.getItemType() + ", ����� ���̵�" + item.getUserId());
		
		int listSize = list.size();
		int result = -1;
		
		for(int i = 0 ; i < listSize; i++) {
			System.out.println("������ �׸� " + list.get(i).getItemName() + "������ Ÿ�� " + item.getItemType() + "����� " + item.getUserId());
			result = iDao.removeItem(item.getItemType(), list.get(i).getItemName(), item.getUserId());
		}
		
		if(result > 0) {
			response.getWriter().append("1");
		} else {
			response.getWriter().append("0");
		}
	}
	
}