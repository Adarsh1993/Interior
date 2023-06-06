package opticvyu.Model;

/**
 * Created by Adarsh on 06-07-2018.
 */

public class navi {
    String id,name;
    int images;

    public navi() {
    }


    public navi(String name, int images) {
        this.name = name;
        this.images = images;
    }

    public navi(String id, String name, int images) {
        this.id = id;
        this.name = name;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
