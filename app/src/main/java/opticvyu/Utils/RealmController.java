package opticvyu.Utils;

import android.content.Context;
import android.util.Log;


import com.optic.opticvyu.customviews.Connection_Alert;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import opticvyu.Interior.InteriorDashboard;
import opticvyu.Model.DrawingImages;
import opticvyu.Model.GetCoordinate.ResponseStItem;
import opticvyu.Model.Project_model;
import opticvyu.Model.Zone;

public class RealmController {

    private Realm realm;

    Connection_Alert connectionAlert;
    public Context context;

    public RealmController(Context context) {
        realm = Realm.getDefaultInstance();
        this.context = context;
    }


    public void addProjects(ArrayList<Project_model> model) {
        realm.beginTransaction();
        realm.insertOrUpdate(model);
        realm.commitTransaction();

    }

    public void AddZone(ArrayList<Zone> zones) {
        realm.beginTransaction();
        realm.insertOrUpdate(zones);
        realm.commitTransaction();

    }

    public void AddDrawing(ArrayList<DrawingImages> mDrawingImages) {
        realm.beginTransaction();
        realm.insertOrUpdate(mDrawingImages);
        realm.commitTransaction();

    }

    public void SaveCoordinates(ArrayList<ResponseStItem> cords) {
        realm.beginTransaction();
        realm.insertOrUpdate(cords);
        realm.commitTransaction();

    }

    public List<Project_model> getProject() {
        List<Project_model> project_model = new ArrayList<Project_model>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Project_model> results = realm
                    .where(Project_model.class)
                    .findAll();

            project_model.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return project_model;


    }

    public List<Zone> getZones(String projectId) {
        List<Zone> zones = new ArrayList<Zone>();
        zones.clear();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Zone> results = realm.where(Zone.class).equalTo("projectid", projectId).findAll();

            zones.addAll(realm.copyFromRealm(results));

        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return zones;

    }

    public List<DrawingImages> getDrawings(String zoneID) {
        List<DrawingImages> drawing = new ArrayList<DrawingImages>();
        drawing.clear();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<DrawingImages> results = realm.where(DrawingImages.class).equalTo("zoneid", zoneID).findAll();

            drawing.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return drawing;

    }

    public List<ResponseStItem> getCoordinate(String drawingID) {
        List<ResponseStItem> cords = new ArrayList<ResponseStItem>();
        cords.clear();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<ResponseStItem> results = realm.where(ResponseStItem.class).equalTo("drawingid", Integer.parseInt(drawingID)).findAll();

            cords.addAll(realm.copyFromRealm(results));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return cords;

    }


    public void DeleteRealmData() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

}
