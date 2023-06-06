package opticvyu.Model.Material;


//[
//        {
//        "entry_id": "string",
//        "lat": "string",
//        "long": "string",
//        "dc_no": "string",
//        "imagepath": "string",
//        "status": "1",
//        "create_at": "2020-10-30T15:15:45.842Z",
//        "id": "string"
//        }
//        ]

public class ImageModel {
    String entry_id,imagepath,dc_no;


    public String getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getDc_no() {
        return dc_no;
    }

    public void setDc_no(String dc_no) {
        this.dc_no = dc_no;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "entry_id='" + entry_id + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", dc_no='" + dc_no + '\'' +
                '}';
    }
}
