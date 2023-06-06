package opticvyu.Model.Material;

public class Forms_data_before_sumbit {
    int i;
    String id;
    char Name;

    public Forms_data_before_sumbit(char name) {
        Name = name;
    }



    public Forms_data_before_sumbit(int i, String id, char name) {
        this.i = i;
        this.id = id;
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getName() {
        return Name;
    }

    public void setName(char name) {
        Name = name;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
