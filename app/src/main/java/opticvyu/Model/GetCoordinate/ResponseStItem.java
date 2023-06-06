package opticvyu.Model.GetCoordinate;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ResponseStItem extends RealmObject {

	@SerializedName("comment_count")
	private int commentCount;

	@SerializedName("currentstatus")
	private String currentstatus;

	@SerializedName("coordinateid")
	private int coordinateid;

	@SerializedName("catethree")
	private String catethree;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("visitdate")
	private String visitdate;

	@SerializedName("updateat")
	private String updateat;

	@SerializedName("title")
	private String title;

	@SerializedName("count360")
	private int count360;

	@SerializedName("createat")
	private String createat;

	@SerializedName("drawingid")
	private int drawingid;

	@SerializedName("countvideos")
	private int countvideos;

	@SerializedName("cateone")
	private String cateone;

	@SerializedName("x")
	private int X;

	@SerializedName("y")
	private int Y;

	@SerializedName("countimages")
	private int countimages;

	@SerializedName("catetwo")
	private String catetwo;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("status")
	private int status;

	public void setCommentCount(int commentCount){
		this.commentCount = commentCount;
	}

	public int getCommentCount(){
		return commentCount;
	}

	public void setCurrentstatus(String currentstatus){
		this.currentstatus = currentstatus;
	}

	public String getCurrentstatus(){
		return currentstatus;
	}

	public void setCoordinateid(int coordinateid){
		this.coordinateid = coordinateid;
	}

	public int getCoordinateid(){
		return coordinateid;
	}

	public void setCatethree(String catethree){
		this.catethree = catethree;
	}

	public String getCatethree(){
		return catethree;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setVisitdate(String visitdate){
		this.visitdate = visitdate;
	}

	public String getVisitdate(){
		return visitdate;
	}

	public void setUpdateat(String updateat){
		this.updateat = updateat;
	}

	public String getUpdateat(){
		return updateat;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setCount360(int count360){
		this.count360 = count360;
	}

	public int getCount360(){
		return count360;
	}

	public void setCreateat(String createat){
		this.createat = createat;
	}

	public String getCreateat(){
		return createat;
	}

	public void setDrawingid(int drawingid){
		this.drawingid = drawingid;
	}

	public int getDrawingid(){
		return drawingid;
	}

	public void setCountvideos(int countvideos){
		this.countvideos = countvideos;
	}

	public int getCountvideos(){
		return countvideos;
	}

	public void setCateone(String cateone){
		this.cateone = cateone;
	}

	public String getCateone(){
		return cateone;
	}

	public void setX(int X){
		this.X = X;
	}

	public int getX(){
		return X;
	}

	public void setY(int Y){
		this.Y = Y;
	}

	public int getY(){
		return Y;
	}

	public void setCountimages(int countimages){
		this.countimages = countimages;
	}

	public int getCountimages(){
		return countimages;
	}

	public void setCatetwo(String catetwo){
		this.catetwo = catetwo;
	}

	public String getCatetwo(){
		return catetwo;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseStItem{" + 
			"comment_count = '" + commentCount + '\'' + 
			",currentstatus = '" + currentstatus + '\'' + 
			",coordinateid = '" + coordinateid + '\'' + 
			",catethree = '" + catethree + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",visitdate = '" + visitdate + '\'' + 
			",updateat = '" + updateat + '\'' + 
			",title = '" + title + '\'' + 
			",count360 = '" + count360 + '\'' + 
			",createat = '" + createat + '\'' + 
			",drawingid = '" + drawingid + '\'' + 
			",countvideos = '" + countvideos + '\'' + 
			",cateone = '" + cateone + '\'' + 
			",x = '" + X + '\'' + 
			",y = '" + Y + '\'' + 
			",countimages = '" + countimages + '\'' + 
			",catetwo = '" + catetwo + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}