package mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.annotation.RequestMapping;

public class UserController {

	@RequestMapping(type = "post", url = "/login")
	public String login(HttpServletRequest req,HttpServletResponse resp){
		System.out.println(req.getParameter("username"));
		try {
			resp.sendRedirect("success.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "login";
	}
}
