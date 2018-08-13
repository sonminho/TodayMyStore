package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ItemDAO;
import dto.ItemDTO;

public class AndroidAddItem implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		int result = -1;
		
		ItemDTO item = gson.fromJson(request.getParameter("item"), ItemDTO.class);
		
		ItemDAO iDao = new ItemDAO();
		
		System.out.println(item.getItemType() + " " + item.getItemName() + " " + item.getUnitPrice() + " " + item.getUserId());
		
		if(iDao.searchItem(item.getItemName()) == 1) { // 중복된 아이템 값 없음
			result = iDao.addItem(item.getItemType(), item.getItemName(), item.getUnitPrice(), item.getUserId());
			
			if(result > 0) {
				response.getWriter().append("1");
				System.out.println("아이템추가성공");
			} else {
				response.getWriter().append("0");
			}
		} else { // 중복된 아이템 값 존재
			response.getWriter().append("-1");
		}
		
	}

}
