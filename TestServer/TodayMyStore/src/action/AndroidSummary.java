package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dao.TxnsDAO;
import dto.SummaryDTO;

public class AndroidSummary implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		SummaryDTO summary = gson.fromJson(request.getParameter("summary"), SummaryDTO.class);
		
		TxnsDAO tDao = new TxnsDAO();
		
		ArrayList<SummaryDTO> list = tDao.summarySales(summary.getUserId(), "export", summary.getStartDate(), summary.getEndDate());
		ArrayList<SummaryDTO> list2 = tDao.summarySales(summary.getUserId(), "import", summary.getStartDate(), summary.getEndDate());
		ArrayList<SummaryDTO> list3 = new ArrayList<>();
		ArrayList<SummaryDTO> list4 = new ArrayList<>();
		
		JsonElement jsonElement = gson.toJsonTree(list);
		JsonElement jsonElement2 = gson.toJsonTree(list2);
		JsonObject jsonObject = new JsonObject();
		
		jsonObject.add("exportList", jsonElement);
		jsonObject.add("importList", jsonElement2);
		
		response.getWriter().append(jsonObject.toString());
	}

}
