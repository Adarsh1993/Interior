package opticvyu.Utils;

public class CsvFile {


    long timeStamp;
    double ax, ay, az, gx, gy, gz;


    public String toCsvString() {
        return String.format("%S,%S,%S,%S,%S,%S,%S,\n" ,ax, ay, az, gx, gy, gz,timeStamp);

    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public double getAz() {
        return az;
    }

    public void setAz(double az) {
        this.az = az;
    }

    public double getGx() {
        return gx;
    }

    public void setGx(double gx) {
        this.gx = gx;
    }

    public double getGy() {
        return gy;
    }

    public void setGy(double gy) {
        this.gy = gy;
    }

    public double getGz() {
        return gz;
    }

    public void setGz(double gz) {
        this.gz = gz;
    }
}

