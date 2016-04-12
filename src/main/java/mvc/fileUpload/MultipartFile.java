package mvc.fileUpload;

import org.apache.commons.fileupload.FileItem;

import java.io.InputStream;

/**
 * created by julingpu on 2016/3/31
 * 上传文件封装类
 **/
public class MultipartFile {
    //表示commons.fileUpload为我们生成的表单中的单个文件对象（文件对象包括普通的form表单信息 也包括提交的文件信息）
   //字段名称
    private String fieldName;
    //文件大小
    private long fileSize ;
    //文件名称
    private String fileName;
    //存储文件的输入流
    private InputStream fileInputStream;
    //文件类型
    private String contentType;
    //文件的字节
    private byte[] bytes;
    private FileItem fileItem;


    public MultipartFile(String fieldName, long fileSize, String fileName, InputStream fileInputStream, String contentType,FileItem fileItem,byte[] bytes) {
        this.fieldName = fieldName;
        this.fileSize = fileSize;

        this.bytes = bytes;
        this.fileInputStream = fileInputStream;
        this.contentType = contentType;
        this.fileItem = fileItem;
    }

    public String getFieldName() {
        return fieldName;
    }
    public long getFileSize() {
        return fileSize;
    }
    public String getFileName() {
        return fileName;
    }
    public InputStream getFileInputStream() {
        return fileInputStream;
    }
    public String getContentType() {
        return contentType;
    }

    public FileItem getFileItem() {
        return fileItem;
    }
    public byte[] getBytes(){
        return bytes;
    }

}
