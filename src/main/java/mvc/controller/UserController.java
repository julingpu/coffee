package mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.annotation.RequestMapping;
import mvc.fileUpload.MultipartFile;

public class UserController {


	@RequestMapping(type = "post", url = "/login")
	public String login(String user, String password, int prize, MultipartFile[] files){

		System.out.println(user);
		System.out.println(password);
		System.out.println(password);
		/*try {
			resp.sendRedirect("success.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return "login";
	}

}
