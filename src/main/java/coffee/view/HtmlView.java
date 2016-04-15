package coffee.view;

/**
 * created by julingpu on 2016/4/13
 **/
public class HtmlView extends ModelAndView{
    public HtmlView(){
        this.setView(View.HTML);
    }
    public HtmlView(String path){
        this.setView(View.HTML);
        this.setPath(path);
    }

    public void setHtmlPath(String path){
        this.setPath(path);
    }
}