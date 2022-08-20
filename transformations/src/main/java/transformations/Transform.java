package transformations;

import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.values.PCollection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ToString;

public class Transform extends PTransform<PCollection<String>, PCollection<String>> {
  private final ObjectMapper mapper = JsonMapper.builder()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
      .findAndAddModules()
      .build();

  private final ParseJsons<DriverLocation> pj = ParseJsons.of(DriverLocation.class).withMapper(mapper);

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
