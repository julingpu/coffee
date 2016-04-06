package mvc.resolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mvc.annotation.RequestInfo;
import mvc.annotation.RequestMapping;


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
	public static void addRequestMapping(RequestInfo requestInfo,Method method){
		
		requestMappingMap.put(requestInfo, method);
	}
	
	/**
	 * 通过requestMapping注解和method添加请求映射
	 * @param requestMapping
	 * @param method
	 */
	public static void addRequestMapping(RequestMapping requestMapping,Method method){
		RequestInfo requestInfo = new RequestInfo(requestMapping.type(),requestMapping.url());
		addRequestMapping(requestInfo,method);
	}
	
	
}
