package mvc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		ModelAndView modelAndView = new ModelAndView(View.JSP);
		modelAndView.setPath("success.jsp");
		return modelAndView;
	}
	@RequestMapping(type = "get", url = "/freemarker")
	public ModelAndView freemarkerTest(UserInfo userInfo,HttpServletRequest request,HttpServletResponse response){
		Map root  = new HashMap<String,Object>();
		root.put("username","我");
		root.put("password","admin");
		ModelAndView modelAndView = new ModelAndView(View.FREEMARKER,"WEB-INF/freemarker/login.ftl",root);
		return modelAndView;
	}


}
