package mvc.test;

import mvc.entity.UserInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * created by julingpu on 2016/4/5
 **/
public class ReflectTest {
    public UserInfo login(String username, String password){
        return new UserInfo(username,password);
    }

    public static void main(String[] args){
        try {
            //获取class对象

            Class c = ReflectTest.class;
            //获取名为login 参数类型为String，String的方法对象
            Method login = c.getMethod("login",String.class,String.class);
            //定义入参
            String username = "root";
            String password = "1234";
            //通过反射执行方法  效果就相当于普通的UserInfo result = new ReflectTest().login(username,password);
            Object result = login.invoke(c.newInstance(),username,password);
            System.out.print(result.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
