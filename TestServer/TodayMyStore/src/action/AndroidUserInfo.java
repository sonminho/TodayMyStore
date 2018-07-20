package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UserDAO;
import dto.UserDTO;

public class AndroidUserInfo implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		UserDTO user = gson.fromJson(request.getParameter("user"), UserDTO.class);
		
		System.out.println("사용자 아이디 : " + user.getmId());
		
		UserDAO uDao = new UserDAO();
		UserDTO userInfo = uDao.userInfo(user.getmId());
		
		if(userInfo != null)
			response.getWriter().append(gson.toJson(userInfo));
		else
			response.getWriter().append("-1");
	}

}
