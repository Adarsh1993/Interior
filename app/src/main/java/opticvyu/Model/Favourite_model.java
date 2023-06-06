package opticvyu.Model;

/**
 * Created by Adarsh on 10-03-2018.
 */

public class Favourite_model {
    String CaptuureTime, AddesTime;
    int imge;

    public Favourite_model() {
    }

    public Favourite_model(String captuureTime, String addesTime, int imge) {
        CaptuureTime = captuureTime;
        AddesTime = addesTime;
        this.imge = imge;
    }

    public String getCaptuureTime() {
        return CaptuureTime;
    }

    public void setCaptuureTime(String captuureTime) {
        CaptuureTime = captuureTime;
    }

    public String getAddesTime() {
        return AddesTime;
    }

    public void setAddesTime(String addesTime) {
        AddesTime = addesTime;
    }

    public int getImge() {
        return imge;
    }

    public void setImge(int imge) {
        this.imge = imge;
    }
}
