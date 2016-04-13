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
    protected ModelAndView() {
    }
    protected ModelAndView(View view) {
        this.view = view;
    }

    protected ModelAndView( View view,Object model) {
        this.model = model;
        this.view = view;
    }

    protected ModelAndView(View view, String path, Object model) {
        this.view = view;
        this.path = path;
        this.model = model;
    }

    protected String getPath() {
        return path;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    protected View getView() {
        return view;
    }

    protected void setView(View view) {
        this.view = view;
    }

    protected Object getModel() {
        return model;
    }

    protected void setModel(Object model) {
        this.model = model;
    }
}
