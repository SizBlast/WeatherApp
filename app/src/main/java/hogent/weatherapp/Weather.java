package hogent.weatherapp;

import android.net.Uri;

/**
 * Created by Christof on 30/12/2014.
 */
public class Weather {
    private String icon;
    private String locaction;
    private double minTemp;
    private double maxTemp;

    public Weather(){
        super();
    }

    public Weather(String icon, String location, double minTemp, double maxTemp) {
        super();
        this.icon = "http://openweathermap.org/img/w/"+icon+".png";
        this.locaction = location;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLocation() {
        return locaction;
    }

    public void setLocation(String loc) {
        this.locaction = loc;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }
}
