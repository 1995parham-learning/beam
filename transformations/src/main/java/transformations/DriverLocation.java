package transformations;

import java.io.Serializable;

import org.joda.time.Instant;

public class DriverLocation implements Serializable {
  private long id;
  private Instant timestamp;
  private double lat;
  private double lng;
  private int bearing;
  private int accuracy;
  private String serviceType;
  private String status;
  private int speed;
  private Instant deviceTimestamp;
  private String channel;

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format("DriverLocation [%d] @ %s", this.id, this.deviceTimestamp.toString());
  }

  public String getTimestamp() {
    return timestamp.toString();
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = Instant.parse(timestamp);
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

  public void setBearing(int bearing) {
    this.bearing = bearing;
  }

  public double getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(int accuracy) {
    this.accuracy = accuracy;
  }

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public String getDeviceTimestamp() {
    return deviceTimestamp.toString();
  }

  public void setDeviceTimestamp(String deviceTimestamp) {
    this.deviceTimestamp = Instant.parse(deviceTimestamp);
  }
}
