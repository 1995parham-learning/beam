package transformations;

import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ToString;


public class Transform extends PTransform<PCollection<String>, PCollection<String>> {

  @Override
  public PCollection<String> expand(PCollection<String> input) {
    return input
            .apply(ParseJsons.of(DriverLocation.class))
            .setCoder(SerializableCoder.of(DriverLocation.class))
            .apply(ToString.elements());
  }
}
