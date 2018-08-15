package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UserDAO;
import dto.UserDTO;

public class AndroidIdCheck implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		UserDTO user = gson.fromJson(request.getParameter("user"), UserDTO.class); // 클라이언트로부터 JSON 객체를 받음
		System.out.println(user.getmId() + " 계정 확인 중");
		
		UserDAO uDao = new UserDAO();
		int result = uDao.userIdCheck(user.getmId()); // 사용자 아이디를 검색 후 테이블에서 삭제
		
		// 계정 확인 완료
		if(result == 1){
			response.getWriter().append("1");
		} else { 
			response.getWriter().append("0");
		}
	}
}