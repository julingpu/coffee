package mvc.dispatcher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mvc.annotation.RequestInfo;
import mvc.util.ClassUtil;
import mvc.util.LoadUtil;
import mvc.util.RequestMappingUtil;

public class DispatcherServlet extends HttpServlet{

	private static Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// TODO Auto-generated method stub
		LoadUtil.init();
		/*ServletContext servletContext = servletConfig.getServletContext();
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping("*");*/
		
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*String uri = req.getRequestURI(); ///mvc/test.action
		String url = req.getRequestURL().toString();//http://localhost/mvc/test.action
		String contextPath = req.getContextPath();  ///mvc
		String getContentType= req.getContentType();//application/x-www-form-urlencoded
		int getServerPort = req.getServerPort();//80
		String getServletPath = req.getServletPath();///test.action
		String getScheme = req.getScheme();//http
		String getServerName =req.getServerName();//localhost
		String getProtocol = req.getProtocol();//HTTP/1.1
		String getMethod = req.getMethod();//POST*/
		
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setType(req.getMethod().toLowerCase());
		requestInfo.setUrl(req.getServletPath());
		
		Method method = RequestMappingUtil.getRequestMapping(requestInfo);
		if(method!=null){
			try {
				Object result = method.invoke(method.getDeclaringClass().newInstance(), new Object[]{req,resp});
				System.out.println(result);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			logger.info("未能找到处理"+requestInfo.toString()+"的方法");
		}
	}
	

}
