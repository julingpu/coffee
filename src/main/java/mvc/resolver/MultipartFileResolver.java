package mvc.resolver;

import mvc.fileUpload.MultipartFile;
import mvc.fileUpload.MultipartServletRequest;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传文件解析器 （使用Commons.fileupload包）
 * created by julingpu on 2016/3/30
 **/
public class MultipartFileResolver {
    //定义一次request请求中上传文件的总大小 超出将拒绝本次请求中的文件  单位是字节
    private static int total_file_max_size = 1000*1024*1024;
    //定义一次request请求中单个上传文件的大小 超出将拒绝此单个文件   单位是字节
    private static int single_file_max_size = 100*1024*1024;
    //定义上传文件的编码方式
    private static String  headerEncoding = "UTF-8";
    //定义内存中存放文件大小的临界值  超出将文件存放在服务器临时文件 单位是字节
    private static int memory_threshold = 10*1024*1024;

    private static FileItemFactory fileItemFactory;
    //定义处理request中上传文件的组件
    private static  ServletFileUpload commonsUpload;
    static {

        //设置文件存放在内存的临界值
        fileItemFactory = new DiskFileItemFactory(memory_threshold,null) ;
        // Create a new file upload handler
        commonsUpload = new ServletFileUpload(fileItemFactory);
        // 设置所有文件大小最大值
        commonsUpload.setSizeMax(total_file_max_size);
        // 设置单个文件大小最大值
        commonsUpload.setFileSizeMax(single_file_max_size);
        //设置文件编码方式
        commonsUpload.setHeaderEncoding(headerEncoding);

    }

    /**
     * 处理上传文件
     * @param request
     * @return   如果有文件上传返回mulitpartServletRequest对象   没有就返回原来的servletRequest对象
     */
    public static HttpServletRequest  handleUploadFile(HttpServletRequest request){
        //检查此次请求中是否包含文件上传
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        //如果包含文件
        if(isMultipart) {
            List<MultipartFile> multipartFiles  = new ArrayList<MultipartFile>();
            List<FileItem> fileItems;
            MultipartServletRequest mulitpartServletRequest = new MultipartServletRequest(request);
            try {
                //获取此次请求中所有的表单信息
                 fileItems = commonsUpload.parseRequest(request);
                for (int i = 0 ; i <fileItems.size();i++){
                    //如果是普通文本信息  添加到mulitpartServletRequest的parameterMap中
                    if(fileItems.get(i).isFormField()){
                        String value = fileItems.get(i).getString(headerEncoding);
                        mulitpartServletRequest.setParameter(fileItems.get(i).getFieldName(),value);
                    }
                    //如果是文件信息 添加到mulitpartServletRequest的parameterMap中
                    else {
                        MultipartFile multipartFile = new MultipartFile(fileItems.get(i));
                        mulitpartServletRequest.addMultipartFile(multipartFile);
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }  finally {
                return mulitpartServletRequest;
            }
        }
        return request;

    }

}
