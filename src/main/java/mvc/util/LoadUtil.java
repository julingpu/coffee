package mvc.util;



import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import mvc.annotation.RequestMapping;
import mvc.resolver.MethodParameterResolver;
import mvc.resolver.RequestMappingResolver;
import mvc.xml.XmlEntity;
import mvc.xml.XmlParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mvc框架装载工具类
 *
 * 装载mvc.xml文件  初始化处理映射器
 *
 */
public class LoadUtil {

	private static Logger logger =LoggerFactory.getLogger(LoadUtil.class);
	

	public static void init(){
		logger.info("开始装载mvc配置文件...");
		XmlEntity xmlEntity = XmlParser.parserXML();
		logger.info("mvc配置文件装载成功");
		String controllerPackageName = (String)xmlEntity.getParamValue("controllerPackage");
		if(StringUtil.checkNotNull(controllerPackageName)) {
			logger.info("开始扫描控制层中的RequestMapping注解...");
			logger.info("扫描的控制层包名为:" + controllerPackageName);
			Set<Class> controllerClassSet = ClassUtil.getClassSetByPackageName(controllerPackageName);
			for (Class<?> class1 : controllerClassSet) {
				Method[] methods = class1.getMethods();
				for (Method method : methods) {
					Annotation[] as = method.getAnnotations();
					if (method.isAnnotationPresent(RequestMapping.class)) {
						logger.info("使用 " + method.getDeclaringClass().getName() + "." + method.getName() + " 处理请求 " + "[type=" + method.getAnnotation(RequestMapping.class).type() + ",url=" + method.getAnnotation(RequestMapping.class).url() + "]");
						RequestMappingResolver.addRequestMapping(method.getAnnotation(RequestMapping.class), method);
					}
				}
			}
			logger.info("控制层中的RequestMapping注解扫描完毕");
		}
		else{
			logger.error("未检测到配置文件中的controllerPackage属性  请求映射处理方法将失效");
		}

		String entityPackageName = (String)xmlEntity.getParamValue("entityPackage");
		if(StringUtil.checkNotNull(controllerPackageName)) {
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
