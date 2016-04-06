package mvc.fileUpload;

import org.apache.commons.fileupload.FileItem;

/**
 * created by julingpu on 2016/3/31
 * 上传文件封装类
 **/
public class MultipartFile {
    //表示commons.fileUpload为我们生成的表单中的单个文件对象（文件对象包括普通的form表单信息 也包括提交的文件信息）
    private FileItem fileItem;
    public MultipartFile(){}
    public MultipartFile(FileItem fileItem){
        this.fileItem = fileItem;
    }

    public FileItem getFileItem() {
        return fileItem;
    }

    public void setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
    }
}
