package coffee.xml;

import java.util.HashMap;
import java.util.Map;


/**
 * mvc.xml文件对应的实体
 * create by julingpu on 2016年3月22日
 *
 */
public class XmlEntity {

	 private Map<String,Object> paramMap = new HashMap<String,Object>();
	 

	 public Object getParamValue(String paramName){
		 return paramMap.get(paramName);
	 }

	 public void setParam(String key,Object value){
		 paramMap.put(key, value);
	 }
	


	
}
