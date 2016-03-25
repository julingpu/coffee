package mvc.util;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类装载工具类
 * create by julingpu on 2016年3月22日
 *
 */
public class ClassUtil {

	private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	
	/**
	 * 装载指定的类
	 * @param className
	 * @return
	 */
	public static Class<?> loadClass(String className){
		Class<?> c;
		try {
			c = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("类装载失败",e);
			throw new RuntimeException(e);
		}
		return c;
	}
	
	/**
	 * 获取指定包名下的所有class对象
	 * @param packageName  指定的包名 如mvc.controller
	 * @return
	 */
	public static Set<Class<?>> getClassSetByPackageName(String packageName){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		//获取包名下的文件
		File file  = new File(StringUtil.formatBlank(Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".","\\")).getFile()));
		//获取文件名下的所有class对象
		classSet = getClassByFilePath(classSet,file.getPath(),packageName);
		return classSet;
	}
	
	/**
	 * 获取指定文件名下的所有class对象
	 * @param classSet
	 * @param filePath		class文件所处的文件夹 
	 * @param packageName   class所在的包名
	 * @return
	 */
	public static Set<Class<?>> getClassByFilePath(Set<Class<?>> classSet,String filePath,String packageName){
		File[] files = new File(filePath).listFiles();
		//遍历文件夹中的文件
		for (File file2 : files) {
			//如果依然是文件夹  继续遍历
			if(file2.isDirectory()){
				getClassByFilePath(classSet,file2.getPath(),packageName);
			}
			//如果是class文件 装载成class对象添加到classSet中
			else if(file2.getName().contains(".class")){
				classSet.add(loadClass(packageName+"."+file2.getName().replace(".class","")));
			}
		}
		return classSet;
	}
	
}
