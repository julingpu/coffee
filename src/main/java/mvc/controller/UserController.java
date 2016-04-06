package mvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.annotation.RequestMapping;
import mvc.entity.UserInfo;
import mvc.fileUpload.MultipartFile;
import mvc.view.ModelAndView;
import mvc.view.View;

public class UserController {

//
	@RequestMapping(type = "post", url = "/login")
	public ModelAndView login(UserInfo userInfo,HttpServletRequest request){


		System.out.println(request.getSession().getServletContext().getRealPath("/"));
		System.out.println(request.getSession().getServletContext().getRealPath(request.getRequestURI()));
		ModelAndView modelAndView = new ModelAndView(View.JSON,userInfo);
		return modelAndView;
	}
	@RequestMapping(type = "get", url = "/freemarker")
	public ModelAndView freemarkerTest(UserInfo userInfo){
		ModelAndView modelAndView = new ModelAndView(View.FREEMARKER,"/login.ftl");
		return modelAndView;
	}

}
