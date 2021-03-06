package coffee.annotation;

/**
 * 请求信息实体
 *
 * 将request中的请求类型和请求地址封装成一个实体  RequestMappingUtil可以根据此对象获取对应的处理方法
 */
public class RequestInfo {
	//请求类型（get post）
	private String type;
	//请求地址
	private String url;
	public RequestInfo() {
		super();
	}
	public RequestInfo(String type, String url) {
		super();
		this.type = type.toLowerCase();
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type.toLowerCase();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "[type=" + type + ", url=" + url + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestInfo other = (RequestInfo) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	
}
