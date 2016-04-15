package coffee.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类注解
 *
 * 当将此注解标注在某个实体类之后 MethodParameterResolver可以将request中的信息构建成一个该实体对象  注入到controller的方法入参中
 * created by julingpu on 2016/4/1
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface  Entity {
}
