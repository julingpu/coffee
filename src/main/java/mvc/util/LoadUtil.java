package mvc.util;



import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import mvc.annotation.RequestMapping;
import mvc.resolver.MethodParameterResolver;
import mvc.resolver.RequestMappingResolver;
import mvc.xml.XmlEntity;
import mvc.xml.XmlParser;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;

/**
 * mvc框架装载工具类
 *
 * 装载mvc.xml文件  初始化处理映射器
 *
 */
public class LoadUtil {

	private static Logger logger =LoggerFactory.getLogger(LoadUtil.class);
	

	public static void init(ServletConfig servletConfig){
		logger.info("开始装载mvc配置文件...");
		String configPath = servletConfig.getInitParameter("contextConfigLocation");
		XmlEntity xmlEntity = null;
		boolean parserResult = true;
		try {
			xmlEntity = XmlParser.parserXML(configPath);
		} catch (DocumentException e) {
			logger.info("mvc配置文件装载失败 失败原因可能是没有添加mvc.xml文件");
			parserResult = false;
		}
		if(parserResult) {
			logger.info("mvc配置文件装载成功");
			String controllerPackageName = (String) xmlEntity.getParamValue("controllerPackage");
			if (StringUtil.checkNotNull(controllerPackageName)) {
				logger.info("开始扫描控制层中的RequestMapping注解...");
				logger.info("扫描的控制层包名为:" + controllerPackageName);
				//获取controller包名下所有的class对象
				Set<Class> controllerClassSet = ClassUtil.getClassSetByPackageName(controllerPackageName);
				//对class对象进行遍历
				for (Class<?> class1 : controllerClassSet) {
					//获取class对象的所有method对象
					Method[] methods = class1.getMethods();
					//对method对象进行遍历
					for (Method method : methods) {
						//判断该method对象是否被RequestMapping注解标注
						Annotation[] as = method.getAnnotations();
						if (method.isAnnotationPresent(RequestMapping.class)) {
							//将注解信息和method对象绑定在一起
							if(RequestMappingResolver.addRequestMapping(method.getAnnotation(RequestMapping.class), method)){
								logger.info("使用 " + method.getDeclaringClass().getName() + "." + method.getName() + " 处理请求 " + "[type=" + method.getAnnotation(RequestMapping.class).type() + ",url=" + method.getAnnotation(RequestMapping.class).url() + "]");
							}
						}
					}
				}
				logger.info("控制层中的RequestMapping注解扫描完毕");
			} else {
				logger.error("未检测到配置文件中的controllerPackage属性  请求映射处理方法将失效");
			}


			String entityPackageName = (String)xmlEntity.getParamValue("entityPackage");
			if(StringUtil.checkNotNull(entityPackageName)) {
				logger.info("开始扫描实体层中的Entity注解...");
				logger.info("扫描的实体层包名为:" + entityPackageName);
				Set<Class> entityClassSet = ClassUtil.getClassSetByPackageName(entityPackageName);
				MethodParameterResolver.setEntityClassSet(entityClassSet);
				logger.info("实体层中的Entity注解扫描完毕");
			}
			else{
				logger.error("未检测到配置文件中的entityPackage属性 将无法为controller方法的入参中自动注入包名为entityPackage下的实体");
			}
		}

	}
}
