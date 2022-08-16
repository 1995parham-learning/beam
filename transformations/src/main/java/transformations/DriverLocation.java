package transformations;

import java.io.Serializable;
import java.time.Instant;

public class DriverLocation implements Serializable {
  private long id;
  private Instant timestamp;

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
}
