package mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.*;
import mvc.entity.UserInfo;
import mvc.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 视图解析器
 * created by julingpu on 2016/4/6
 **/
public class ViewResolver {
    private static Logger logger = LoggerFactory.getLogger(ViewResolver.class);
    //freemarker version
    static Version freemarek_version = new Version(2, 3, 1);
    //freemarker configuration
    static Configuration freemarker_config = new Configuration(freemarek_version);
    //定义freemarker文件目录  其实就是工程目录
    static File freemarker_directory =null;

    public static  void handlerView(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response){
        try {
            switch (modelAndView.getView()){
                case JSON:
                    handleJson(modelAndView.getModel(),response);
                    break;
                case JSP:
                    handleJsp(modelAndView.getPath(),request,response);
                    break;
                case FREEMARKER:
                    handleFreemarker(modelAndView.getPath(),modelAndView.getModel(),response);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ServletException e) {
            e.printStackTrace();
        }catch (TemplateException e) {
            e.printStackTrace();
        }
        finally {
        }
    }

    private static void handleFreemarker(String path,Object model,HttpServletResponse response) throws IOException, TemplateException {
        Template temp = freemarker_config.getTemplate(path,"GBK");
        temp.process(model, response.getWriter());
    }


    public static void handleJson(Object object,HttpServletResponse response) throws IOException {
        String result = new ObjectMapper().writeValueAsString(object);
        logger.info("返回给客户端的json:"+result);
        //这里如果不设置contentType 就算设置了response的characterEncoding为UTF-8也会乱码
        //因为如果不设置response就会默认当做
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(result);
    }
    public static void handleJsp(String path,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request,response);
    }


    /**
     * 初始化视图解析器的相关配置
     * @param request
     * @throws IOException
     */
    public static void init(HttpServletRequest request) throws IOException {
        //init freemarker
        initFreemarker(request);
    }

    /**
     * 初始化freemarker相关组件
     * @param request
     * @throws IOException
     */
    public static void initFreemarker(HttpServletRequest request) throws  IOException{
        if (freemarker_directory==null){
            freemarker_directory = new File(WebUtil.getContextPath(request));
            freemarker_config.setDirectoryForTemplateLoading(freemarker_directory);
            freemarker_config.setObjectWrapper(new DefaultObjectWrapper(freemarek_version));
            freemarker_config.setDefaultEncoding("UTF-8");
        }
    }
}
