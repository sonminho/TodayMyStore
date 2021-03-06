package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.TxnsDAO;
import dto.ItemDTO;

public class AndroidTxnInsert implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		ArrayList<ItemDTO> list = gson.fromJson(request.getParameter("itemList"), new TypeToken<ArrayList<ItemDTO>>(){}.getType());
		
		String userId = request.getParameter("userId");
		String type = request.getParameter("type");
		String date = request.getParameter("date");
		
		System.out.println(userId + " , " + type);
		
		TxnsDAO tDao = new TxnsDAO();
		
		int result = tDao.txnInsert(list, date);
		
		for(int i = 0 ; i < list.size() ; i++) {
			System.out.print(list.get(i).getItemName()+" ");
			System.out.print(list.get(i).getItemType()+" ");
			System.out.print(list.get(i).getUserId()+" ");
			System.out.print(list.get(i).getItemNameId()+" ");
			System.out.print(list.get(i).getUnitPrice()+" ");
			System.out.print(list.get(i).getItemCount()+" ");
			System.out.println();
		}
		
		System.out.println("��� " + result);
		response.getWriter().append(result+"");
	}

}
