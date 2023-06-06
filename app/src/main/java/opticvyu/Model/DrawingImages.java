package opticvyu.Model;

import java.io.Serializable;

import io.realm.RealmObject;

public class  DrawingImages extends RealmObject implements Serializable {
    String drawingid,drawingpath,name,projectid,zoneid;

    public String getDrawingid() {
        return drawingid;
    }

    public void setDrawingid(String drawingid) {
        this.drawingid = drawingid;
    }

    public String getDrawingpath() {
        return drawingpath;
    }

    public void setDrawingpath(String drawingpath) {
        this.drawingpath = drawingpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getZoneid() {
        return zoneid;
    }

    public void setZoneid(String zoneid) {
        this.zoneid = zoneid;
    }

    @Override
    public String toString() {
        return getName();
    }
}
