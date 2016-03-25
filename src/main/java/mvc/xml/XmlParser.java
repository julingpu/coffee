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
public class XmlParser {

	private static Logger logger =LoggerFactory.getLogger(XmlParser.class);
	
	//定义配置文件名称
	private static  String xmlPath = "mvc.xml";
	
	//定义配置文件对应的实体
	private  XmlEntity xmlEntity = new XmlEntity();
	
	/**
	 * 解析mvc.xml文件
	 * @param filePath
	 * @return
	 */
	public  XmlEntity parserXML() {
		SAXReader reader = new SAXReader();
		File file = null;
		try {
			//这里需要把getResource获取到的资源的地址中的转义字符%20手动转成空格
			file = new File(StringUtil.formatBlank(Thread.currentThread().getContextClassLoader().getResource(xmlPath).getFile()));
			Document document = reader.read(file);
			Element root = document.getRootElement();
			//解析param节点
			parseParam(root);
			//解析requestMapping节点
			parseRequestMapping(root);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error("mvc.xml解析错误");
		} catch (MyException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMsg());
		}

		return xmlEntity;
	}
	
	/**
	 * 解析xml文件中的param节点
	 * @param root
	 */
	public void parseParam(Element root){
		List<Element> paramElements = root.elements("param");
		for (Element element : paramElements) {
			xmlEntity.setParam(element.attributeValue("name"), element.attributeValue("value"));
		}
	}
	
	/**
	 * 解析xml文件中的requestMapping节点
	 * @param root
	 * @throws MyException 当requestMapping中的属性为空时抛出异常
	 */
	public void parseRequestMapping(Element root) throws MyException {
		List<Element> requestMappingElements = root.elements("requestMapping");
		for (Element element : requestMappingElements) {
			XmlRequestMappingInfo xmlRequestMappingInfo = new XmlRequestMappingInfo();
			if(element.attributeValue("type")==null){
				xmlRequestMappingInfo.setType("GET");
			}
			if(StringUtil.checkNull(element.attributeValue("url"),element.attributeValue("class"),element.attributeValue("method"))){
				xmlRequestMappingInfo.setUrl(element.attributeValue("url"));
				xmlRequestMappingInfo.setClazz(element.attributeValue("class"));
				xmlRequestMappingInfo.setMethod(element.attributeValue("method"));
				xmlEntity.setXmlRequestMappingInfo(xmlRequestMappingInfo);
			}else{
				throw new MyException("mvc.xml文件中的requestMapping节点的元素值不能为空");
			}
			
		}
	}
	
	public XmlEntity getXmlEntity(){
		return xmlEntity;
	}
}
