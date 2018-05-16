package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UserDAO;
import dto.UserDTO;

public class AndroidJoin implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		int result = -1;
		
		UserDTO user = gson.fromJson(request.getParameter("user"), UserDTO.class);
		System.out.println(user.getmId() + " " + user.getmPw()
					+ " " + user.getmName() + " " + user.getmEmail()
				 	+ " " + user.getmPhone());
		
		UserDAO uDao = new UserDAO();
		result = uDao.userJoin(user.getmId(), user.getmPw(), user.getmName(), user.getmEmail(), user.getmPhone());
		
		if(result > 0) {
			response.getWriter().append("1");
		} else {
			response.getWriter().append("0");
		}
	}
}