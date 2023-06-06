package opticvyu.Model.Material;

public  class Draft_model {
    String entry_name;
    String date;
    String code;
    String id;

    public Draft_model(String entry_name, String date, String code, String id) {
        this.entry_name = entry_name;
        this.date = date;
        this.code = code;
        this.id = id;
    }

    public String getEntry_name() {
        return entry_name;
    }

    public void setEntry_name(String entry_name) {
        this.entry_name = entry_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
