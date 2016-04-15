package coffee.xml;

import java.io.File;
import java.util.List;
import coffee.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml文件解析类 create by julingpu on 2016年3月22日
 * 
 */
public  class XmlParser {

	private static Logger logger =LoggerFactory.getLogger(XmlParser.class);
	
	//定义配置文件名称
	private static  String xmlPath = "coffee.xml";
	
	//定义配置文件对应的实体
	private static  XmlEntity xmlEntity = new XmlEntity();
	
	/**
	 * 解析mvc.xml文件
	 * @return
	 */
	public static XmlEntity  parserXML(String path) throws DocumentException {
		SAXReader reader = new SAXReader();
		File file = null;
		//如果web.xml中的contextConfigLocation不为空 那么使用用户手动设置的配置文件路径
		if(StringUtil.checkNotNull(path)){
			//这里需要把getResource获取到的资源的地址中的转义字符%20手动转成空格
			if(path.split(":").length>1){
				path = path.split(":")[1];
			}
			file = new File(StringUtil.formatBlank(Thread.currentThread().getContextClassLoader().getResource(path).getFile()));
		}else {
			file = new File(StringUtil.formatBlank(Thread.currentThread().getContextClassLoader().getResource(xmlPath).getFile()));
		}
		Document document = reader.read(file);
		Element root = document.getRootElement();
		//解析param节点
		parseParam(root);
		return xmlEntity;
	}
	
	/**
	 * 解析xml文件中的param节点
	 * @param root
	 */
	public static void parseParam(Element root){
		List<Element> paramElements = root.elements("param");
		for (Element element : paramElements) {
			xmlEntity.setParam(element.attributeValue("name"), element.attributeValue("value"));
		}
	}
	

	public XmlEntity getXmlEntity(){
		return xmlEntity;
	}
}
