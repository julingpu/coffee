package mvc.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;










import mvc.exception.MyException;
import mvc.util.StringUtil;

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
	private static  String xmlPath = "mvc.xml";
	
	//定义配置文件对应的实体
	private static  XmlEntity xmlEntity = new XmlEntity();
	
	/**
	 * 解析mvc.xml文件
	 * @return
	 */
	public static XmlEntity  parserXML() {
		SAXReader reader = new SAXReader();
		File file = null;
		try {
			//这里需要把getResource获取到的资源的地址中的转义字符%20手动转成空格
			file = new File(StringUtil.formatBlank(Thread.currentThread().getContextClassLoader().getResource(xmlPath).getFile()));
			Document document = reader.read(file);
			Element root = document.getRootElement();
			//解析param节点
			parseParam(root);

		} catch (DocumentException e) {
			logger.error("mvc.xml解析错误");
		}

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
