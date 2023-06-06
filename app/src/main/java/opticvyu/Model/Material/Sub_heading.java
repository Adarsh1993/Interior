package opticvyu.Model.Material;


//{"subheading":"Reinforcement-Steel","material_heading_id":2,"status":1,"create_at":"2020-10-09T15:41:32.592Z","id":1}
public class Sub_heading {
    String subheading;
    String id;

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return subheading;
    }
}
