package mvc.view;

/**
 * 数据视图实体
 * created by julingpu on 2016/4/6
 **/
public class ModelAndView {
    //定义视图类型
    private View view;
    //定义视图路径
    private String path;
    //定义视图包含的数据
    private Object model;
    public ModelAndView() {
    }
    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView( View view,Object model) {
        this.model = model;
        this.view = view;
    }

    public ModelAndView(View view, String path, Object model) {
        this.view = view;
        this.path = path;
        this.model = model;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}
