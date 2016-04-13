package mvc.view;

/**
 * created by julingpu on 2016/4/13
 **/
public class JspView extends ModelAndView{
    public JspView(){
        this.setView(View.JSP);
    }
    public JspView(String path){
        this.setView(View.JSP);
        this.setPath(path);
    }
    public void setJspPath(String path){
        this.setPath(path);
    }
}