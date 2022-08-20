package transformations;

import java.io.Serializable;
import java.time.Instant;

public class DriverLocation implements Serializable {
  private long id;
  private Instant timestamp;
  private double lat;
  private double lng;
  private double bearing;
  private double accuracy;
  private double serviceType;
  private double status;
  private double speed;
  private double deviceTimestamp;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format("DriverLocation [%d]", this.id);
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getBearing() {
    return bearing;
  }

  public void setBearing(double bearing) {
    this.bearing = bearing;
  }

  public double getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(double accuracy) {
    this.accuracy = accuracy;
  }

  public double getServiceType() {
    return serviceType;
  }

  public void setServiceType(double serviceType) {
    this.serviceType = serviceType;
  }

  public double getStatus() {
    return status;
  }

  public void setStatus(double status) {
    this.status = status;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public double getDeviceTimestamp() {
    return deviceTimestamp;
  }

  public void setDeviceTimestamp(double deviceTimestamp) {
    this.deviceTimestamp = deviceTimestamp;
  }
}
