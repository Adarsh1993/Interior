package opticvyu.Model.GetCoordinate;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSt{

	@SerializedName("ResponseSt")
	private List<ResponseStItem> responseSt;

	public void setResponseSt(List<ResponseStItem> responseSt){
		this.responseSt = responseSt;
	}

	public List<ResponseStItem> getResponseSt(){
		return responseSt;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSt{" + 
			"responseSt = '" + responseSt + '\'' + 
			"}";
		}
}