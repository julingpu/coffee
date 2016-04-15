package coffee.dispatcher;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *通过commons.fileUpload处理文件上传使用示例
 * created by julingpu on 2016/4/5
 **/
public class FileServlet  extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断是否包含文件上传
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if(isMultipart) {
            //定义一次request请求中上传文件的总大小 超出将拒绝本次请求中的文件  单位是字节
            int total_file_max_size = 1000 * 1024 * 1024;
            //定义一次request请求中单个上传文件的大小 超出将拒绝此单个文件   单位是字节
            int single_file_max_size = 100 * 1024 * 1024;
            //定义上传文件的编码方式
            String headerEncoding = "UTF-8";
            //定义内存中存放文件大小的临界值  超出将文件存放在服务器临时文件 单位是字节
            int memory_threshold = 10 * 1024 * 1024;
            //定义解析request中inputSteam的组件（第一个参数是内存存放临界值  第二个参数是定义超过临界值时文件存放在硬盘中的位置 null代表使用默认值javax.servlet.context.tempdir）
            FileItemFactory fileItemFactory = new DiskFileItemFactory(memory_threshold, null);
            ServletFileUpload commonsUpload = new ServletFileUpload(fileItemFactory);
            // 设置所有文件大小最大值
            commonsUpload.setSizeMax(total_file_max_size);
            // 设置单个文件大小最大值
            commonsUpload.setFileSizeMax(single_file_max_size);
            //设置文件编码方式
            commonsUpload.setHeaderEncoding(headerEncoding);
            try {
                //获取对request的inputStream的解析结果  每个表单中的元素就是一个FileItem  isFormField为true代表普通元素 false代表文件元素
                List<FileItem> fileItems = commonsUpload.parseRequest(req);
            } catch (FileUploadException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
