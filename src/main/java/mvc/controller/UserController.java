package mvc.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.annotation.RequestMapping;
import mvc.entity.UserInfo;
import mvc.fileUpload.MultipartFile;
import mvc.util.TimeUtil;
import mvc.view.ModelAndView;
import mvc.view.View;

public class UserController {

//
	@RequestMapping(type = "post", url = "/login")
	public ModelAndView login(UserInfo userInfo,HttpServletRequest request,MultipartFile[] files){
		ModelAndView modelAndView = new ModelAndView(View.JSP);
		if(files.length>0){
			File directory =new File(request.getSession().getServletContext().getRealPath("/")+"images");
			if  (!directory .exists()  && !directory .isDirectory())
			{
				directory .mkdir();
			}

		}
		for (MultipartFile file : files) {
			if(file.getFileSize()>0){
				Integer random_number = new Random().nextInt(1000);
				System.out.println("random_number:"+random_number);
				//文件命名采用随机数加时间的方式  不使用源文件的名称是因为可能含有中文
				String time = TimeUtil.getStringTime1();
				String path = request.getSession().getServletContext().getRealPath("/")+"images"+ File.separator+random_number+"_"+time+".jpg";
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(new File(path));
					out.write(file.getBytes());
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
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
	@RequestMapping(type = "get", url = "/json")
	public ModelAndView jsonTest(){
		Map root  = new HashMap<String,Object>();
		root.put("username","我");
		root.put("password","admin");
		ModelAndView modelAndView = new ModelAndView(View.JSON,root);
		return modelAndView;
	}

}
