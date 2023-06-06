package opticvyu.Model.Material;


//{"material_heading":"Steel","projectid":1,"status":1,"userid":3,"create_at":"2020-10-09T15:41:32.447Z","id":1}

public class Heading {
    String material_heading;
    String id;

    public String getMaterial_heading() {
        return material_heading;
    }

    public void setMaterial_heading(String material_heading) {
        this.material_heading = material_heading;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return material_heading;
    }
}
