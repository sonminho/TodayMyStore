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
		
		UserDTO user = gson.fromJson(request.getParameter("user"), UserDTO.class); // Ŭ���̾�Ʈ�κ��� JSON ��ü�� ����
		System.out.println(user.getmId() + " ���� Ȯ�� ��");
		
		UserDAO uDao = new UserDAO();
		int result = uDao.userIdCheck(user.getmId()); // ����� ���̵� �˻� �� ���̺��� ����
		
		// ���� Ȯ�� �Ϸ�
		if(result == 1){
			response.getWriter().append("1");
		} else { 
			response.getWriter().append("0");
		}
	}
}