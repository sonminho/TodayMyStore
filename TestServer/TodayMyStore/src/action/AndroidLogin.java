package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UserDAO;
import dto.UserDTO;

public class AndroidLogin implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Gson gson = new Gson();
		
		UserDTO user = gson.fromJson(request.getParameter("user"), UserDTO.class);
		System.out.println(user.getmId() + " " + user.getmPw());
		
		UserDAO uDao = new UserDAO();
		int result = uDao.userCheck(user.getmId(), user.getmPw());
		
		// 계정 확인 완료
		if(result == 1){
			response.getWriter().append("1");
		} else { 
			response.getWriter().append("0");
		}
		
	}
}