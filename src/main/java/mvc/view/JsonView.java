package mvc.view;

/**
 * created by julingpu on 2016/4/13
 **/
public class JsonView extends ModelAndView{
    public JsonView(){
        this.setView(View.JSON);
    }
    public JsonView(Object model){
        this.setView(View.JSON);
        this.setModel(model);
    }
    public void setJsonModel(Object model){
        this.setModel(model);
    }
}