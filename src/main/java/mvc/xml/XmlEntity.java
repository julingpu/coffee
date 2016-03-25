package mvc.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * mvc.xml文件对应的实体
 * create by julingpu on 2016年3月22日
 *
 */
public class XmlEntity {

	 private Map<String,Object> paramMap = new HashMap<String,Object>();
	 
	 private List<XmlRequestMappingInfo> XmlRequestMappingInfos = new ArrayList<XmlRequestMappingInfo>();
	 
	 public Object getParamValue(String paramName){
		 return paramMap.get(paramName);
	 }

	 public void setParam(String key,Object value){
		 paramMap.put(key, value);
	 }
	
	 public void setXmlRequestMappingInfo(XmlRequestMappingInfo requestMappingInfo){
		 XmlRequestMappingInfos.add(requestMappingInfo);
	 }
	
	 public List<XmlRequestMappingInfo> getXmlRequestMappingInfo(){
		 return XmlRequestMappingInfos;
	 }

	
}