package transformations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import org.apache.beam.sdk.testing.NeedsRunner;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;

public class TransformationsTest {
  @Rule
  public TestPipeline p = TestPipeline.create();

  @Test
  public void dlsParsing() throws Exception {
    List<String> dls = List.of(
        """
            {
              "id":7,
              "lat":38.241,
              "lng":48.314884,
              "bearing":120,
              "accuracy":68,
              "timestamp":"2022-08-20T14:33:58.238766989Z",
              "serviceType":"ECO",
              "status":"AVAILABLE",
              "speed":7,
              "deviceTimestamp":"2022-08-20T14:33:59Z"
            }
                    """,
        """
            {
              "id":20,
              "lat":38.241,
              "lng":48.314884,
              "bearing":120,
              "accuracy":68,
              "timestamp":"2022-08-20T14:33:58.238766989Z",
              "serviceType":"ECO",
              "status":"AVAILABLE",
              "speed":7,
              "deviceTimestamp":"2022-08-20T14:33:59Z"
            }
            """,
        """
            {
              "id":1378,
              "lat":38.241,
              "lng":48.314884,
              "bearing":120,
              "accuracy":68,
              "timestamp":"2022-08-20T14:33:58.238766989Z",
              "serviceType":"ECO",
              "status":"AVAILABLE",
              "speed":7,
              "deviceTimestamp":"2022-08-20T14:33:59Z"
            }
            """);

    PCollection<String> output = p.apply(Create.of(dls)).apply(Transform.on());

    PAssert.that(output).containsInAnyOrder(
        "DriverLocation [1378] @ 2022-08-20T14:33:59.000Z",
        "DriverLocation [7] @ 2022-08-20T14:33:59.000Z",
        "DriverLocation [20] @ 2022-08-20T14:33:59.000Z");

    p.run().waitUntilFinish();
  }
}
