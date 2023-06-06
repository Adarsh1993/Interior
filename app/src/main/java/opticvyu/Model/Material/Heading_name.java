package opticvyu.Model.Material;
//{
//        "materila_name": "rod-8",
//        "subheading_id": "0",
//        "unit": "string",
//        "asset_type": "string",
//        "status": 1,
//        "create_at": "2020-10-13T04:04:44.418Z",
//        "id": "5f853a41123602ece25b0cfc"
//        }
public class Heading_name {
    String materila_name;
    String id,unit,asset_type;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getMaterila_name() {
        return materila_name;
    }

    public void setMaterila_name(String materila_name) {
        this.materila_name = materila_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return materila_name;
    }
}
