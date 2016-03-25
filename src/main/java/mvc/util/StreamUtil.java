package mvc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 * create by julingpu on 2016年3月22日
 *
 */
public class StreamUtil {

	/**
	 * 将输入流转换成String
	 * @param is
	 * @return
	 */
	public static String getStringFromStream(InputStream is){
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		try {
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
		
	}
}
