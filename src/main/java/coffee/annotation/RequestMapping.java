package coffee.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 映射注解
 *
 * 将此注解标注在某个方法上之后 RequestMappingResolver会根据注解信息和方法信息构建成请求映射装入到requestMappingMap中
 * create by julingpu on 2016年3月22日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {

	//http请求的方法（有POST GET DELETE UPDATE几种）
	public String type() default "GET";
	//http请求的地址
	public String url();
	
}
