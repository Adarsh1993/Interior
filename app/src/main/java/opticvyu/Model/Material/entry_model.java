package opticvyu.Model.Material;

import android.os.Parcel;
import android.os.Parcelable;

//{
//        "projectid": 1,
//        "userid": 331,
//        "status": 1,
//        "create_at": "2020-11-13T04:49:38.710Z",
//        "enterystatus": "Draft",
//        "id": "5fae1062f9dcc2a79da33aa2",
//        "UserName": "Adarsh Jain",
//        "entry_name": "sand8",
//        "supplier_name": "hshs"
//        }
    public class entry_model  implements Parcelable {

    String projectid, userid, enterystatus, id,create_at,entry_name,UserName,supplier_name;


    protected entry_model(Parcel in) {
        projectid = in.readString();
        userid = in.readString();
        enterystatus = in.readString();
        id = in.readString();
        create_at = in.readString();
        entry_name = in.readString();
        UserName = in.readString();
        supplier_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(projectid);
        dest.writeString(userid);
        dest.writeString(enterystatus);
        dest.writeString(id);
        dest.writeString(create_at);
        dest.writeString(entry_name);
        dest.writeString(UserName);
        dest.writeString(supplier_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<entry_model> CREATOR = new Creator<entry_model>() {
        @Override
        public entry_model createFromParcel(Parcel in) {
            return new entry_model(in);
        }

        @Override
        public entry_model[] newArray(int size) {
            return new entry_model[size];
        }
    };

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEnterystatus() {
        return enterystatus;
    }

    public void setEnterystatus(String enterystatus) {
        this.enterystatus = enterystatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getEntry_name() {
        return entry_name;
    }

    public void setEntry_name(String entry_name) {
        this.entry_name = entry_name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }


    @Override
    public String toString() {
        return "entry_model{" +
                "projectid='" + projectid + '\'' +
                ", userid='" + userid + '\'' +
                ", enterystatus='" + enterystatus + '\'' +
                ", id='" + id + '\'' +
                ", create_at='" + create_at + '\'' +
                ", entry_name='" + entry_name + '\'' +
                ", UserName='" + UserName + '\'' +
                ", supplier_name='" + supplier_name + '\'' +
                '}';
    }

    public entry_model() {
    }
}
