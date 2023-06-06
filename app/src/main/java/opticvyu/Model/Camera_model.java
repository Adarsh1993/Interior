package opticvyu.Model;

import java.io.Serializable;

/**
 * Created by Adarsh on 18-03-2018.
 */

public class Camera_model implements Serializable {
    String Camera;
    String camera_id;
    String camera_active, camera_angles;
    String camera_type;
    String cam_360;
    String cctv;
    String channel_1;
    String vod;
    String ptz;
    String cctv_id;
    String cctv_pwd;
    String cctv_port;


    public String getPtz() {
        return ptz;
    }

    public void setPtz(String ptz) {
        this.ptz = ptz;
    }

    public String getCctv_id() {
        return cctv_id;
    }

    public void setCctv_id(String cctv_id) {
        this.cctv_id = cctv_id;
    }

    public String getCctv_pwd() {
        return cctv_pwd;
    }

    public void setCctv_pwd(String cctv_pwd) {
        this.cctv_pwd = cctv_pwd;
    }

    public String getCctv_port() {
        return cctv_port;
    }

    public void setCctv_port(String cctv_port) {
        this.cctv_port = cctv_port;
    }

    public void setCctv(String cctv) {
        this.cctv = cctv;
    }

    public void setChannel_1(String channel_1) {
        this.channel_1 = channel_1;
    }

    public String getCamera_angles() {
        return camera_angles;
    }

    public void setCamera_angles(String camera_angles) {
        this.camera_angles = camera_angles;
    }

    public String getCamera_type() {
        return camera_type;
    }

    public void setCamera_type(String camera_type) {
        this.camera_type = camera_type;
    }



    public String getCamera() {
        return Camera;
    }

    public void setCamera(String camera) {
        Camera = camera;
    }

    public String getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
    }

    public String getCam_360() {
        return cam_360;
    }

    public String getCctv() {
        return cctv;
    }

    public String getChannel_1() {
        return channel_1;
    }

    public void setCam_360(String cam_360) {
        this.cam_360 = cam_360;
    }

    public String getCamera_active() {
        return camera_active;
    }

    public void setCamera_active(String camera_active) {
        this.camera_active = camera_active;
    }

    @Override
    public String toString() {
        return getCamera();
    }

    public String getVod() {
        return vod;
    }

    public void setVod(String vod) {
        this.vod = vod;
    }
}
