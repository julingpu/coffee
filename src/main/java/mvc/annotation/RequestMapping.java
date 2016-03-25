package mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 映射注解
 * create by julingpu on 2016年3月22日
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {

	//http请求的方法（有POST GET DELETE UPDATE几种）
	String type() default "GET";
	//http请求的地址
	String url();
	
}
