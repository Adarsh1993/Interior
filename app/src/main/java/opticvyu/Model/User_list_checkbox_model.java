package opticvyu.Model;

/**
 * Created by Adarsh on 22-05-2018.
 */

public class User_list_checkbox_model {

    private boolean selected;
    String  project_name,project_id;


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
}
