package transformations;

import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.ToString;

import com.google.gson.Gson;

public class Transform extends PTransform<PCollection<String>, PCollection<String>> {

  @Override
  public PCollection<String> expand(PCollection<String> input) {
    return input.apply(ParDo.of(new DoFn<String, DriverLocation>() {
      @DoFn.ProcessElement
      public void processElement(ProcessContext c) {
        Gson gson = new Gson();
        c.output(gson.fromJson(c.element(), DriverLocation.class));
      }
    }))
        .apply(ToString.elements());
  }
}
