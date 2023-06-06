package opticvyu.Model;

public class TimeLine {
    String Imagelink;
    String name;
    String Id;
    String url;

    public TimeLine() {
    }

    public TimeLine(String imagelink, String name, String id) {
        Imagelink = imagelink;
        this.name = name;
        Id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagelink() {
        return Imagelink;
    }

    public void setImagelink(String imagelink) {
        Imagelink = imagelink;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
