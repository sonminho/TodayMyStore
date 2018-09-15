package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.TxnsDAO;
import dto.TrendDTO;

public class AndroidTxnTrend implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		TrendDTO trend = gson.fromJson(request.getParameter("trend"), TrendDTO.class);
		
		System.out.println(trend.toString());
		
		TxnsDAO txnsDAO = new TxnsDAO();
		
		ArrayList<TrendDTO> list = txnsDAO.txnTrend(trend.getUserId(), trend.getItemType(),
				trend.getStartDate(), trend.getEndDate());
		
		response.getWriter().append(gson.toJson(list));
	}

}
