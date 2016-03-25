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
	public static boolean checkNull(String... str){
		for (String string : str) {
			if(string==null||"".equals(string))
				return false;
		}
		return true;
		
	}
	
}
