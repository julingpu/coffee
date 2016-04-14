package mvc.dispatcher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.fileUpload.MultipartFile;
import mvc.fileUpload.MultipartServletRequest;
import mvc.resolver.MultipartFileResolver;
import mvc.resolver.MethodParameterResolver;
import mvc.util.WebUtil;
import mvc.view.ModelAndView;
import mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mvc.annotation.RequestInfo;
import mvc.util.LoadUtil;
import mvc.resolver.RequestMappingResolver;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * mvc框架的核心转发器
 *
 * mvc框架的入口  完成请求映射处理器的初始化  完成请求映射  请求入参和请求返回值的处理
 */
public class DispatcherServlet extends HttpServlet{

	private static Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	/**
	 * 初始化mvc框架  当第一次访问此servlet时执行
	 * @param servletConfig
	 * @throws ServletException
     */
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		logger.info("初始化"+this.getClass().getName()+"开始");
		long begin = System.currentTimeMillis();
		LoadUtil.init(servletConfig);
		long end = System.currentTimeMillis();
		logger.info("初始化"+this.getClass().getName()+"结束  耗时"+(end-begin)+"毫秒");
	}


	/**
	 * 核心处理器
	 * 每次请求都会执行
	 * 处理文件上传  controller方法参数注入 执行 及结果处理
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
     */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//E:\workspaces\intellij-workspace\mvc\out\artifacts\mvc_war_exploded\
		//System.out.println( req.getSession().getServletContext().getRealPath("/"));
		//初始化viewResolver的相关配置
		ViewResolver.init(req);
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		//处理文件上传  如果没有文件上传 返回的是旧的request 如果有文件上传  返回的是新封装的request
		HttpServletRequest progressRequest = MultipartFileResolver.handleUploadFile(req);
		boolean hasHandleFileUpload = false;
		if(progressRequest == req){
			logger.info("没有处理过上传文件");
		}else{
			hasHandleFileUpload = true;
		}
		//根据request信息通过映射处理器找到对应的处理方法
		Method method = RequestMappingResolver.getRequestMapping(req);
		StringBuilder requestInfo = new StringBuilder();
		requestInfo.append("[type=").append(req.getMethod().toLowerCase()).append(", url=").append(req.getServletPath()).append("]");
		if(method!=null) {
			logger.info("使用 "+method.getDeclaringClass().getName()+"."+method.getName()+" 处理请求 "+requestInfo);
			try {
				//获取controller方法入参值
				Object[] params = MethodParameterResolver.getMethodParamValue(method,progressRequest,resp);
				ModelAndView result = null;
				//获取spring的容器
				WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
				if(webApplicationContext!=null){
					//这里首先从spring容器中查找controller的bean  因为spring可以帮助controller注入service或者dao组件
					//如果直接是method.invoke(method.getDeclaringClass().newInstance(),params );  那么我们使用的controller中的自动注入都会无效  因为其并不是spring管理的bean
					Object controllerInstance = webApplicationContext.getBean(method.getDeclaringClass());
					if(controllerInstance==null){
						logger.info("没有使用spring的bean");
						result = (ModelAndView) method.invoke(method.getDeclaringClass().newInstance(),params );
					}else {
						logger.info("使用spring的bean");
						result = (ModelAndView) method.invoke(controllerInstance, params);
					}
				}else{
					logger.info("没有使用spring的bean");
					result = (ModelAndView) method.invoke(method.getDeclaringClass().newInstance(), params);
				}


				//通过反射执行controller方法并获取controller方法返回值

				if(result!=null){
					//解析返回值
					ViewResolver.handlerView(result,req,resp);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}finally {
				logger.info(method.getName()+"处理"+requestInfo+"结束");
			}
		}
		else{
			logger.info("未能找到处理"+requestInfo+"的方法");
		}
		//如果处理过上传文件  清空文件缓存
		if(hasHandleFileUpload){
			cleanupMultipart(req);
		}
	}




	/**
	 * 清空文件缓存
	 * @param request
     */
	protected  void cleanupMultipart(HttpServletRequest request) {
		MultipartServletRequest multipartServletRequest = WebUtil.getNativeRequest(request,MultipartServletRequest.class);
		if(multipartServletRequest!=null){
			List<MultipartFile> multipartFiles = multipartServletRequest.getMultipartFiles();
			logger.info("准备清空"+multipartFiles.size()+"个临时文件");
			for (MultipartFile multipartFile : multipartFiles) {
				multipartFile.getFileItem().delete();
			}
			logger.info("临时文件清空完成");
		}
	}

	public void test(){
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
		//E:\workspaces\intellij-workspace\mvc\out\artifacts\mvc_war_exploded\
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.service(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.service(req, resp);
	}
}
