package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.TxnsDAO;
import dto.TxnsDTO;

public class AndroidTxnUpdate implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		ArrayList<TxnsDTO> list = gson.fromJson(request.getParameter("txnList"), new TypeToken<ArrayList<TxnsDTO>>(){}.getType());
		System.out.println("ssssssssssssssssss");
		String userId = request.getParameter("userId");
		
		String type = request.getParameter("type");
		String date = request.getParameter("date");
		
		System.out.println(userId + " , " + type);
		
		for(int i = 0 ; i < list.size() ; i++) {
			System.out.print(list.get(i).getItem_name()+" ");
			System.out.print(list.get(i).getItem_type()+" ");
			System.out.print(list.get(i).getMname_id()+" ");
			System.out.print(list.get(i).getItem_name_id()+" ");
			System.out.print(list.get(i).getUnit_price()+" ");
			System.out.print(list.get(i).getQuantity()+" ");
			System.out.println();
		}
		
		TxnsDAO tDao = new TxnsDAO();
		
		int result = tDao.txnUpdate(list, date);
		
		if(result == 1) {
			System.out.println("���� ����");
		}
	}

}