package mvc.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;

/**
 * created by julingpu on 2016/3/31
 **/
public class WebUtil {

    /**
     * 根据给定的类型将httpServletRequest向上转型
     * @param request
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getNativeRequest(ServletRequest request, Class<T> requiredType) {
        if (requiredType != null) {
            if (requiredType.isInstance(request)) {
                return (T) request;
            }
            else if (request instanceof ServletRequestWrapper) {
                return getNativeRequest(((ServletRequestWrapper) request).getRequest(), requiredType);
            }
        }
        return null;
    }
}
