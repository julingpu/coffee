package mvc.util;

/**
 * 字符串操作类
 * create by julingpu on 2016年3月23日
 *
 */
public class StringUtil {

	/**
	 * 将字符串中空格的转义字符转成空格
	 * @param str
	 * @return
	 */
	public static String formatBlank(String str){
		return str.replace("%20"," ");
	}
	
	/**
	 * 判断字符串是否为空  
	 * @param str
	 * @return 不为空返回true  为空返回false
	 */
	public static boolean checkNotNull(String... str){
		for (String string : str) {
			if(string==null||"".equals(string))
				return false;
		}
		return true;
		
	}

	/**
	 * 通过get方法获取参数名
	 * @param method
	 * @return
     */
	public static String  getFieldFromGetMethod(String method){
		//获取get方法中的大写字符 一般为属性的第一个字符
		String s = method.substring(3,4);
		//将属性的第一个字符变成小写 再将后面的字符进行拼接
		return s.toLowerCase()+method.substring(4);
	}
}
