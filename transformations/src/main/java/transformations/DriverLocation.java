package transformations;

import java.io.Serializable;
import java.time.Instant;

public class DriverLocation implements Serializable {
  private long id;
  private Instant timestamp;
  private double lat;
  private double lng;

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
}
