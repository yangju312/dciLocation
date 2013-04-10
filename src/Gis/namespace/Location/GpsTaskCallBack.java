package Gis.namespace.Location;

import Gis.namespace.Location.GpsTask.GpsData;

public interface GpsTaskCallBack {
public void gpsConnected(GpsData gpsdata);
    
    public void gpsConnectedTimeOut();
}
