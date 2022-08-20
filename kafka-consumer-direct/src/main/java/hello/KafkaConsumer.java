package hello;

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Values;
import org.apache.kafka.common.serialization.StringDeserializer;

import transformations.Transform;

import com.google.common.collect.ImmutableMap;

public class KafkaConsumer {
  public interface KafkaConsumerOptions extends PipelineOptions {
    @Description("kafka bootstrap servers")
    @Default.String("127.0.0.1:9094")
    String getBootstrapServers();

    void setBootstrapServers(String value);
  }

  public static void main(String[] args) {
    System.out.println("Welcome to Elahe beam testing");
    for (String arg : args) {
      System.out.println(arg);
    }

    KafkaConsumerOptions options = PipelineOptionsFactory.fromArgs(args).withValidation()
        .as(KafkaConsumerOptions.class);
    options.setRunner(DirectRunner.class);

    // Create the Pipeline object with the options we defined above.
    Pipeline p = Pipeline.create(options);

    p.apply(KafkaIO.<String, String>read()
        .withBootstrapServers(options.getBootstrapServers())
        .withTopic("dls-elahe")
        .withKeyDeserializer(StringDeserializer.class)
        .withValueDeserializer(StringDeserializer.class)
        .withConsumerConfigUpdates(ImmutableMap.of("auto.offset.reset", (Object) "latest"))

        // We're writing to a file, which does not support unbounded data sources. This
        // line makes it bounded to
        // the first 5 records.
        // In reality, we would likely be writing to a data source that supports
        // unbounded data, such as BigQuery.
        .withMaxNumRecords(5)

        .withoutMetadata() // PCollection<KV<Long, String>>
    )
        .apply(Values.create())
        .apply(new Transform())
        .apply(TextIO.write().to("elahe-dls-"));

    p.run().waitUntilFinish();
  }
}
