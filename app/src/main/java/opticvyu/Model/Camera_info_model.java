package opticvyu.Model;

/**
 * Created by Adarsh on 07-06-2018.
 */

public class Camera_info_model {
    String Camera,Last_image,status;


    public String getCamera() {
        return Camera;
    }

    public void setCamera(String camera) {
        Camera = camera;
    }

    public String getLast_image() {
        return Last_image;
    }

    public void setLast_image(String last_image) {
        Last_image = last_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
