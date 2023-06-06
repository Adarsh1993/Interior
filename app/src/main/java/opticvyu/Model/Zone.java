package opticvyu.Model;

import java.io.Serializable;

import io.realm.RealmObject;

public class Zone extends RealmObject implements Serializable {
    String projectid, zoneid,zonename,status;

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

    public String getZonename() {
        return zonename;
    }

    public void setZonename(String zonename) {
        this.zonename = zonename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return getZonename();

    }
}
