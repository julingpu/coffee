package mvc.resolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import mvc.exception.RequestMappingRepeatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mvc.annotation.RequestInfo;
import mvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * 请求映射解析器
 *
 * 根据request请求中的信息找出映射的处理方法
 * create by julingpu on 2016年3月23日
 *
 */
public class RequestMappingResolver {

	private static Logger logger = LoggerFactory.getLogger(RequestMappingResolver.class);
	//定义请求映射的集合
	private static Map<RequestInfo, Method> requestMappingMap = new HashMap<RequestInfo, Method>();


	/**
	 * 根据request获取对应的method对象
	 * @param request
	 * @return
     */
	public static Method getRequestMapping(HttpServletRequest request){

		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setType(request.getMethod().toLowerCase());
		requestInfo.setUrl(request.getServletPath());
		return requestMappingMap.get(requestInfo);
	}
	/**
	 * 根据url获取对应的method对象
	 * @return
	 */
	public static Method getRequestMapping(RequestInfo requestInfo){
		return requestMappingMap.get(requestInfo);
	}
	
	/**
	 * 通过requestInfo和method添加请求映射
	 * @param requestInfo
	 * @param method
	 */
	public static boolean addRequestMapping(RequestInfo requestInfo,Method method) {
		if(requestMappingMap.get(requestInfo)==null) {
			requestMappingMap.put(requestInfo, method);
			return true;
		}else{
			logger.error(method.getDeclaringClass().getName()+"."+method.getName()+"上的注解无效");
			logger.error("因为"+requestInfo.toString()+"已经映射到方法"+requestMappingMap.get(requestInfo).getDeclaringClass().getName()+"."+requestMappingMap.get(requestInfo).getName());
			return  false;
		}
	}
	
	/**
	 * 通过requestMapping注解和method添加请求映射
	 * @param requestMapping
	 * @param method
	 */
	public static boolean addRequestMapping(RequestMapping requestMapping,Method method){
		RequestInfo requestInfo = new RequestInfo(requestMapping.type(),requestMapping.url());
		return addRequestMapping(requestInfo,method);
	}
	
	
}
