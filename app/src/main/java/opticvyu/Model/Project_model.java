package opticvyu.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Adarsh on 18-03-2018.
 */

public class Project_model extends RealmObject implements Serializable {
    @PrimaryKey
    private  String Project_id;

    String Project_name, project_active,company,material,interior,const_camera;
    private boolean checked;

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getProject_id() {
        return Project_id;
    }

    public void setProject_id(String project_id) {
        Project_id = project_id;
    }

    public String getProject_name() {
        return Project_name;
    }

    public void setProject_name(String project_name) {
        Project_name = project_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProject_active() {
        return project_active;
    }

    public void setProject_active(String project_active) {
        this.project_active = project_active;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getConst_camera() {
        return const_camera;
    }

    public void setConst_camera(String const_camera) {
        this.const_camera = const_camera;
    }

    @Override

    public String toString() {
        return getProject_name();

    }
}
