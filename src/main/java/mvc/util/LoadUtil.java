package mvc.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import mvc.annotation.RequestMapping;
import mvc.xml.XmlEntity;
import mvc.xml.XmlParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoadUtil {

	private static Logger logger =LoggerFactory.getLogger(LoadUtil.class);
	

	
	public static void init(){
		logger.info("开始装载mvc配置文件");
		XmlEntity xmlEntity = new XmlParser().parserXML();
		logger.info("mvc配置文件装载成功");
		String packageName = (String)xmlEntity.getParamValue("package");
		Set<Class<?>> classeSet =ClassUtil.getClassSetByPackageName(packageName);
		for (Class<?> class1 : classeSet) {
			Method[] methods = class1.getMethods();
			for (Method method : methods) {
				Annotation[] as= method.getAnnotations();
				if(method.isAnnotationPresent(RequestMapping.class)){
					RequestMappingUtil.addRequestMapping(method.getAnnotation(RequestMapping.class), method);
				}
			}
		}
		
	}
}
