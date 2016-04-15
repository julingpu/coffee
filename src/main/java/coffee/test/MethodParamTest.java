package coffee.test;

import coffee.resolver.MethodParameterResolver;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MethodParamTest {

    //@org.junit.Test
    public void test() throws Exception {
        Class c = TestClass.class;
        Method[] methods = c.getMethods();
        String s1 = "1";
        String s2 = "2";
         Method m = c.getMethod("a",  new Class[]{String.class,String.class});
        String[] paramNames = MethodParameterResolver.getMethodParameterNamesByAsm4(TestClass.class,m);
        for(int i = 0 ; i < paramNames.length ; i++){
            System.out.println(paramNames[i]);
        }
      //  m.invoke(new TestClass(),s2,s1);
    }

    //@org.junit.Test
    public void testClassTransfer() throws Exception {
            Father f = new Father();
            f = new Children();
            Father f1 = (Father) f;
                System.out.print(new Father() instanceof  Children);//false
            System.out.print(f1 instanceof  Children);//true
    }

    //@org.junit.Test
    public void testMethod() throws Exception {
        Method a = Father.class.getDeclaredMethod("a",Integer.class,Float.class);
        a.invoke(a.getDeclaringClass().newInstance(),null);
    }

    @org.junit.Test
    public void testMethodParamTypes() throws Exception {
        Method a = Father.class.getDeclaredMethod("a",int.class,float.class);
        Class<?>[] classes = a.getParameterTypes();
        for (Class<?> aClass : classes) {
            System.out.println(aClass.getName());
        }
    }
}