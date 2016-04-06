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
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * created by julingpu on 2016/4/6
 **/
public class ViewResolver {
    private static Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    public static  void handlerView(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response){
        try {
            switch (modelAndView.getView()){
                case JSON:
                    handleJson(modelAndView.getModel(),response);
                    break;
                case JSP:
                    handleJsp(modelAndView.getModel().toString(),request,response);
                    break;
                case FREEMARKER:
                    handleFreemarker((String)modelAndView.getModel(),request,response);
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

    private static void handleFreemarker(String path,HttpServletRequest request,HttpServletResponse response) throws IOException, TemplateException {
        Version version = new Version(2, 3, 1);
        Configuration cfg = new Configuration(version);
        cfg.setDirectoryForTemplateLoading(new File(WebUtil.getContextPath(request)+"WEB-INF/freemarker"));
        cfg.setObjectWrapper(new DefaultObjectWrapper(version));
        Template temp = cfg.getTemplate(path);
        /*Map root = new HashMap();
        root.put("name", "张三");
        root.put("address", "中国-北京");*/
        Writer out = new OutputStreamWriter(System.out);
        temp.process(null, response.getWriter());
        out.flush();
    }


    public static void handleJson(Object object,HttpServletResponse response) throws IOException {
        String result = new ObjectMapper().writeValueAsString(object);
        response.getWriter().write(result);
    }
    public static void handleJsp(String path,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request,response);
    }
}
