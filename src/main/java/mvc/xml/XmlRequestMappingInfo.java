package mvc.xml;

/**
 * mvc.xml文件中RequestMapping元素中对应的实体
 * create by julingpu on 2016年3月23日
 *
 */
public class XmlRequestMappingInfo {
	private String type;
	private String url;
	private String clazz;
	private String method;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public XmlRequestMappingInfo(String type, String url, String clazz,
			String method) {
		super();
		this.type = type;
		this.url = url;
		this.clazz = clazz;
		this.method = method;
	}
	public XmlRequestMappingInfo() {
		super();
	}
	
}
