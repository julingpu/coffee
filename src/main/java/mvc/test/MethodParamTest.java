package mvc.test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MethodParamTest {

    @org.junit.Test
    public void testMain() throws Exception {
        Class c = TestClass.class;
        Method[] methods = c.getMethods();
        String s1 = "1";
        String s2 = "2";
        System.out.print("11");
        // Method m = c.getMethod("a",  new Class[]{String.class,String.class});
        //m.invoke(new TestClass(),s2,s1);
    }
}