package mvc.util;
import mvc.fileUpload.MultipartFile;
import mvc.fileUpload.MultipartServletRequest;
import org.objectweb.asm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * controller方法参数工具类
 *
 * 处理controller方法的入参
 * created by julingpu on 2016/4/1
 **/
public class MethodParameterUtils {


    private static Logger logger = LoggerFactory.getLogger(MethodParameterUtils.class);
    /**
     * 获取指定类指定方法的参数名
     *
     * @param clazz 要获取参数名的方法所属的类
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表，如果没有参数，则返回null
     */
    public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes == null || parameterTypes.length == 0) {
            return null;
        }
        final Type[] types = new Type[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            types[i] = Type.getType(parameterTypes[i]);
        }
        final String[] parameterNames = new String[parameterTypes.length];

        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(".");
        className = className.substring(lastDotIndex + 1) + ".class";
        InputStream is = clazz.getResourceAsStream(className);
        try {
            ClassReader classReader = new ClassReader(is);
            classReader.accept(new ClassVisitor(Opcodes.ASM4) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    // 只处理指定的方法
                    Type[] argumentTypes = Type.getArgumentTypes(desc);
                    if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
                        return null;
                    }
                    return new MethodVisitor(Opcodes.ASM4) {
                        @Override
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                            // 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this
                            if (Modifier.isStatic(method.getModifiers())) {
                                parameterNames[index] = name;
                            }
                            else if (index > 0) {
                                parameterNames[index - 1] = name;
                            }
                        }
                    };

                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parameterNames;
    }


    /**
     * 获取controller方法参数值
     * @param method
     * @param request
     * @return
     */
    public static Object[] getMethodParamValue(Method method , HttpServletRequest request,HttpServletResponse response){
        System.out.println("user:"+request.getParameter("user"));
        System.out.println("password:"+request.getParameter("password"));
        MultipartServletRequest multipartRequest = new MultipartServletRequest(request);
        //检测request请求中是否包含上传文件
        Enumeration<String> asd =  request.getParameterNames();
        while(asd.hasMoreElements()){
            System.out.print(asd.nextElement());
        }
        if (request instanceof MultipartServletRequest) {
            multipartRequest = (MultipartServletRequest)request;
            request = (HttpServletRequest) multipartRequest.getRequest();

        }
        //获取controller方法的参数类型
        final Class<?>[] parameterTypes = method.getParameterTypes();
        //获取controller方法的参数名称
        String[] paramNames = getMethodParameterNamesByAsm4(method.getDeclaringClass(),method);
        List<Object> paramValues = new ArrayList<Object>();
        if(parameterTypes.length!=paramNames.length)
            logger.error("方法"+method.getName()+"参数解析错误");
        else {
            for (int i = 0; i < parameterTypes.length; i++) {
                Class paramType = parameterTypes[i];
                //Enumeration<String> e =  request.getParameterNames();
                String val =request.getParameter(paramNames[i]);
                //如果不为空 那么说明是简单类型
                if(val!=null){
                    if (paramType.equals(String.class)) {
                        paramValues.add(val);
                    }else if(paramType.equals(int.class)){
                        paramValues.add(resolveInt(val));
                    }
                    else if(paramType.equals(Integer.class)){
                        paramValues.add(resolveInteger(val));
                    }
                    else if(paramType.equals(float.class)){
                        paramValues.add(resolveFloat(val));
                    }
                    else if(paramType.equals(Float.class)){
                        paramValues.add(resolveFloat1(val));
                    }
                    else if(paramType.equals(double.class)){
                        paramValues.add(resolveDouble(val));
                    }
                    else if(paramType.equals(Double.class)){
                        paramValues.add(resolveDouble1(val));
                    }
                    else if(paramType.equals(Integer.class)){
                        paramValues.add(resolveInteger(val));
                    }
                    else if(paramType.equals(Integer.class)){
                        paramValues.add(resolveInteger(val));
                    }
                }
                //如果为空  可能是request response MultipartFile还有实体等复杂类型   当然也有可能真的为空
                else {
                    if (paramType.equals(HttpServletRequest.class)) {
                        paramValues.add(request);
                    }
                    else if (paramType.equals(HttpServletResponse.class)) {
                        paramValues.add(response);
                    }
                    //如果方法入参中有MultipartFile[]参数  说明用户需要上传文件参数
                    else if(paramType.equals(MultipartFile[].class)){
                            List<MultipartFile> mulitipartFiles =  multipartRequest.getMultipartFiles();
                            paramValues.add(mulitipartFiles.toArray());
                    }
                    else{
                        logger.info(paramNames[i]+"注入失败 request中没有相关属性");
                    }

                }

            }
        }
        return paramValues.toArray();
    }
    public static int resolveInt(String value){
        return new Integer(value);
    }
    public static Integer resolveInteger(String value){
        return new Integer(value);
    }
    public static float resolveFloat(String value){
        return new Float(value);
    }
    public static Float resolveFloat1(String value){
        return new Float(value);
    }
    public static double resolveDouble(String value){
        return new Double(value);
    }
    public static Double resolveDouble1(String value){
        return new Double(value);
    }
}
