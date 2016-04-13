package mvc.view;

/**
 * created by julingpu on 2016/4/13
 **/
public class FreeMarkerView extends ModelAndView{
    public FreeMarkerView(){
        this.setView(View.FREEMARKER);
    }
    public FreeMarkerView(String path){
        this.setView(View.FREEMARKER);
        this.setPath(path);
    }
    public FreeMarkerView(Object model){
        this.setView(View.FREEMARKER);
        this.setModel(model);
    }
    public FreeMarkerView(String path,Object model){
        this.setView(View.FREEMARKER);
        this.setPath(path);
        this.setModel(model);
    }
    public void setFreemarkerPath(String path){
        this.setPath(path);
    }
    public void setFreemarkerModel(Object model){
        this.setModel(model);
    }
}
