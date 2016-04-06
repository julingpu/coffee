package mvc.resolver;
import mvc.fileUpload.MultipartFile;
import mvc.fileUpload.MultipartServletRequest;
import mvc.util.StringUtil;
import org.objectweb.asm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * controller方法参数自动注入解析器
 *
 * 处理controller方法的入参
 * created by julingpu on 2016/4/1
 **/
public class MethodParameterResolver {


    private static Logger logger = LoggerFactory.getLogger(MethodParameterResolver.class);

    //使用@entity注解的类集合
    private static Set<Class> entityClassSet;



    //方法需要注入的参数值
    static List<Object> paramValues = new ArrayList<Object>();
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
        logger.info("开始注入"+method.getDeclaringClass().getName()+"."+method.getName()+"的入参...");
        //获取controller方法的参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        //获取controller方法的参数名称
        String[] paramNames = getMethodParameterNamesByAsm4(method.getDeclaringClass(),method);

        if(paramNames==null||parameterTypes==null){
            //如果方法没有参数 不处理
        }
        else if(parameterTypes.length!=paramNames.length)
            logger.error("方法"+method.getDeclaringClass().getName()+"."+method.getName()+"参数解析错误");
        else {
            for (int i = 0; i < parameterTypes.length; i++) {
                Class paramType = parameterTypes[i];
                //当请求中包含文件的话 页面form上的enctype为multipart/form-data
                //页面的表单信息都统统添加到request payLoad信息中 从原始的HttpServletRequest中调用getParameter获取不到表单中的值
                //所以需要当我们解析request中的文件信息时 将普通的表单信息手动的添加到MultipartServletRequest的parametersMap中去 然后重写getParameter方法
                //那么这里调用的实际上是我们重写后的getParameter方法
                String val =request.getParameter(paramNames[i]);
                //如果不为空 那么说明是简单类型
                if(val!=null){
                    handleSimpleType(paramType,val);
                }
                //如果为空  可能是request response MultipartFile还有实体等复杂类型   当然也有可能真的为空
                else {
                    if (paramType.equals(HttpServletRequest.class)||paramType.equals(HttpServletResponse.class)) {
                       handleServletType(request,response,paramType);
                    }
                    //如果方法入参中有MultipartFile[]参数  说明用户需要上传文件参数
                    else if(paramType.equals(MultipartFile[].class)){
                        handleMultipartType(paramNames[i],request);
                    }
                    else if(entityClassSet.contains(paramType)){
                        handleEntityType(paramType,request);
                    }
                    else{
                        paramValues.add(null);
                        logger.info("参数"+paramNames[i]+"注入失败 请求中没有相关属性");
                    }
                }
            }
        }
        logger.info(method.getDeclaringClass().getName()+"."+method.getName()+"方法入参注入完成");
        return paramValues.toArray();
    }

    private static void handleEntityType(Class entity,HttpServletRequest request) {
        try {
            Object ins = entity.newInstance();
            Method[] methods = entity.getDeclaredMethods();
            for (Method method : methods) {
                //获取所有的get方法
                if(method.getName().startsWith("set")) {
                    //获取get方法的字段
                    String field = StringUtil.getFieldFromGetMethod(method.getName());
                    String val = request.getParameter(field);
                    //如果request中有这个字段的值
                    if (StringUtil.checkNotNull(val)){
                        Class[] fieldTypes = method.getParameterTypes();
                        Object args = null;
                        if (fieldTypes.length == 1&&checkIsSimpleType(fieldTypes[0])) {
                            Class fieldType = fieldTypes[0];
                            if (fieldType.equals(String.class)) {
                                args = val;
                            } else if (fieldType.equals(int.class)) {
                                args = resolveInt(val);
                            } else if (fieldType.equals(Integer.class)) {
                                args = resolveInteger(val);
                            } else if (fieldType.equals(float.class)) {
                                args = resolveFloat(val);
                            } else if (fieldType.equals(Float.class)) {
                                args = resolveFloat1(val);
                            } else if (fieldType.equals(double.class)) {
                                args = resolveDouble(val);
                            } else if (fieldType.equals(Double.class)) {
                                args = resolveDouble1(val);
                            }
                            try {
                                method.invoke(ins, args);
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }

            paramValues.add(ins);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 处理servlet类型参数
     * @param request
     * @param response
     * @param paramType
     * @return
     */
    private static void handleServletType(HttpServletRequest request,HttpServletResponse response,Class paramType ) {
        if(paramType.equals(HttpServletRequest.class)) {
            if(request instanceof  MultipartServletRequest) {
                paramValues.add(((MultipartServletRequest) request).getRequest());
            }
            else{
                paramValues.add(request);
            }
        }
        else if(paramType.equals(HttpServletResponse.class)){
            paramValues.add(response);
        }
    }

    /**
     * 处理文件类型参数
     * @param paramName    方法参数名
     * @param request      httpServletRequest对象
     * @return
     */
    private static void handleMultipartType(String paramName,HttpServletRequest request) {
        MultipartFile[] multipartFileArray = null;
        //如果请求中确实包含上传文件
        if(request instanceof  MultipartServletRequest) {
            //将httpServletRquest强制向上转型
            MultipartServletRequest multipartRequest = (MultipartServletRequest) request;
            //转型成功后获取MultipartServletRequest中的List<MultipartFile> 并手动转成MultipartFile[] 因为我们约定controller方法入参中的文件类型是MultipartFile[]
            List<MultipartFile> mulitipartFiles = multipartRequest.getMultipartFiles();
            multipartFileArray = new MultipartFile[mulitipartFiles.size()];
            for (int j = 0; j < mulitipartFiles.size(); j++) {
                multipartFileArray[j] = mulitipartFiles.get(j);
            }
            paramValues.add(multipartFileArray);
        }else{
            logger.info("参数"+paramName+"请求中并未包含文件上传");
            paramValues.add(multipartFileArray);
        }
    }

    /**
     * 处理简单类型参数
     * @param paramType     参数类型
     * @param val           参数值
     * @return
     */
    private static void handleSimpleType(Class paramType,String val) {
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

    public static void setEntityClassSet(Set<Class> entityClassSet) {
        MethodParameterResolver.entityClassSet = entityClassSet;
    }

    public static boolean checkIsSimpleType(Class fieldType){
        if(fieldType.equals(int.class)||fieldType.equals(Integer.class)||
                fieldType.equals(float.class)||fieldType.equals(Float.class)||
                fieldType.equals(Double.class)||fieldType.equals(double.class)||
                fieldType.equals(String.class)){
            return  true;
        }
        else{
            return false;
        }
    }
}
