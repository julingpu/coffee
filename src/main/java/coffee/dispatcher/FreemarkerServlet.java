package coffee.dispatcher;

import freemarker.template.*;
import coffee.util.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * created by julingpu on 2016/4/7
 **/
public class FreemarkerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("freemarker");
        req.setAttribute("username","aaa");

        Version version = new Version(2, 3, 1);
        Configuration cfg = new Configuration(version);
        cfg.setDirectoryForTemplateLoading(new File(WebUtil.getContextPath(req)+"WEB-INF/freemarker"));
        cfg.setObjectWrapper(new DefaultObjectWrapper(version));
        cfg.setDefaultEncoding("UTF-8");
        Template temp = cfg.getTemplate("login.ftl","UTF-8");
        Map root = new HashMap();
        root.put("username","张三");
        Writer out = new OutputStreamWriter(System.out);
        try {
            temp.process(root, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
        }
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
