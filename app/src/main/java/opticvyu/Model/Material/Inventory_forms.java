package opticvyu.Model.Material;

// {
//    "material_name_id": "5f853a16123602ece25b0cf9",
//    "material_heading_id": "5f881f8725067e15b3debbdd",
//    "sub_heading_id": "5f87cbf425067e15b3debbcf",
//    "dc_no": "dc testing",
//    "vehicle_no": "gvuhvvvh",
//    "supplier_name": "goodthgh",
//    "quantity": "25558858",
//    "rate": "586256",
//    "entry_id": "5f8ae4735d80c21c2f880d71",
//    "status": "1",
//    "create_at": "2020-10-17T12:34:02.148Z",
//    "id": "5f8ae4ba5d80c21c2f880d74",
//    "material_name": "rod",
//    "material_heading": "sand test",
//    "sub_heading": "Sand 1",
//    "desc": "tgg",
//    "remark": "rgdg"
//  }

public  class Inventory_forms {
    String material_name_id,material_heading_id,sub_heading_id,dc_no,vehicle_no,
            supplier_name,quantity,rate,desc,remark,material_name,material_heading,sub_heading,entry_id,id,unit,purchase_order_number;


    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getMaterial_heading() {
        return material_heading;
    }

    public void setMaterial_heading(String material_heading) {
        this.material_heading = material_heading;
    }

    public String getSub_heading() {
        return sub_heading;
    }

    public void setSub_heading(String sub_heading) {
        this.sub_heading = sub_heading;
    }

    public String getMaterial_name_id() {
        return material_name_id;
    }

    public void setMaterial_name_id(String material_name_id) {
        this.material_name_id = material_name_id;
    }

    public String getMaterial_heading_id() {
        return material_heading_id;
    }

    public void setMaterial_heading_id(String material_heading_id) {
        this.material_heading_id = material_heading_id;
    }

    public String getSub_heading_id() {
        return sub_heading_id;
    }

    public void setSub_heading_id(String sub_heading_id) {
        this.sub_heading_id = sub_heading_id;
    }

    public String getDc_no() {
        return dc_no;
    }

    public void setDc_no(String dc_no) {
        this.dc_no = dc_no;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public String getPurchase_order_number() {
        return purchase_order_number;
    }

    public void setPurchase_order_number(String purchase_order_number) {
        this.purchase_order_number = purchase_order_number;
    }

    @Override
    public String toString() {
        return "Inventory_forms{" +
                "material_name_id='" + material_name_id + '\'' +
                ", material_heading_id='" + material_heading_id + '\'' +
                ", sub_heading_id='" + sub_heading_id + '\'' +
                ", dc_no='" + dc_no + '\'' +
                ", vehicle_no='" + vehicle_no + '\'' +
                ", supplier_name='" + supplier_name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", rate='" + rate + '\'' +
                ", desc='" + desc + '\'' +
                ", remark='" + remark + '\'' +
                ", material_name='" + material_name + '\'' +
                ", material_heading='" + material_heading + '\'' +
                ", sub_heading='" + sub_heading + '\'' +
                ", entry_id='" + entry_id + '\'' +
                ", id='" + id + '\'' +
                ", unit='" + unit + '\'' +
                ", purchase_order_number='" + purchase_order_number + '\'' +
                '}';
    }
}
