package opticvyu.Model.Material;

public class SupplyModel {
    String supplier_name,project_id;

    public SupplyModel() {
    }

    public SupplyModel(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    @Override
    public String toString() {
       return getSupplier_name();
    }
}
