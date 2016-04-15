package coffee.fileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对HttpServletRequest的封装类
 * created by julingpu on 2016/3/31
 **/
public class MultipartServletRequest extends HttpServletRequestWrapper {

    //存储此次请求包含的parameter
    private Map<String,String> parametersMap = new HashMap<String,String>();

    //存储此次请求包含的文件
    private List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public MultipartServletRequest(HttpServletRequest request) {
        super(request);
    }

    public MultipartServletRequest(HttpServletRequest request, List<MultipartFile> multipartFiles) {
        super(request);
        this.multipartFiles = multipartFiles;
    }

    public void setParameter(String key,String value){
        parametersMap.put(key,value);
    }

    public String getParameter(String key){
        return parametersMap.get(key);
    }

    public void addMultipartFile(MultipartFile multipartFile) {
        this.multipartFiles.add(multipartFile);
    }

    public List<MultipartFile> getMultipartFiles() {
        return multipartFiles;
    }

   /* public String getParameter(){
        return this.r
    }*/
    public void setMultipartFiles(List<MultipartFile> multipartFiles) {
        this.multipartFiles = multipartFiles;
    }
}

