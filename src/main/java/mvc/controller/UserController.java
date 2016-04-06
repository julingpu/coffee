package mvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.annotation.RequestMapping;
import mvc.entity.UserInfo;
import mvc.fileUpload.MultipartFile;

public class UserController {

//
	@RequestMapping(type = "post", url = "/login")
	public String login(UserInfo userInfo){
		System.out.println(userInfo.toString());
		/*System.out.println(files.length);
		System.out.println(user);
		System.out.println(password);
		System.out.println(prize);*/
		/*try {
			resp.sendRedirect("success.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return "login";
	}

}
