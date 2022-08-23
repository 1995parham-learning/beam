package transformations;

import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.values.PCollection;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ToString;

public class Transform extends PTransform<PCollection<String>, PCollection<String>> {
  private final ParseJsons<DriverLocation> pj = ParseJsons.of(DriverLocation.class);

  public static Transform on() {
    return new Transform();
  }

  private Transform() {
    super();
  }

  @Override
  public PCollection<String> expand(PCollection<String> input) {
    return input
        .apply(this.pj)
        .setCoder(SerializableCoder.of(DriverLocation.class))
        .apply(ToString.elements());
  }
}
