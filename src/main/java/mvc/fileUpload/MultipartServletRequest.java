package mvc.fileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * created by julingpu on 2016/3/31
 **/
public class MultipartServletRequest extends HttpServletRequestWrapper {
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

    public List<MultipartFile> getMultipartFiles() {
        return multipartFiles;
    }

    public void setMultipartFiles(List<MultipartFile> multipartFiles) {
        this.multipartFiles = multipartFiles;
    }
}

