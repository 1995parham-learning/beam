package transformations;

import org.junit.Test;

import java.util.List;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;

public class TransformationsTest {
  @Test
  public void dlsParsing() {
    List<String> dls = List.of(
        "{ \"id\": 1378, \"timestamp\": \"2022-08-16T09:12:43.431351502Z\" }",
        "{ \"id\": 7, \"timestamp\": \"2022-08-16T09:12:43.431351502Z\" }",
        "{ \"id\": 20, \"timestamp\": \"2022-08-16T09:12:43.431351502Z\" }");

    Pipeline p = TestPipeline.create();

    PCollection<String> output = p.apply(Create.of(dls)).apply(Transform.on());

    PAssert.that(output).containsInAnyOrder(
        "DriverLocation [1378]",
        "DriverLocation [7]",
        "DriverLocation [20]");
  }
}
