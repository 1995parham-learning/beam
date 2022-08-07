package hello;

import com.google.common.collect.ImmutableMap;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumer {
    static final String TOKENIZER_PATTERN = "[^\\p{L}]+";

    public static void main(String[] args) {
        System.out.println("Welcome to Elahe beam testing");
        for (String arg: args) {
          System.out.println(arg);
        }

        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(PipelineOptions.class);

        // Create the Pipeline object with the options we defined above.
        Pipeline p = Pipeline.create(options);

        p.apply(KafkaIO.<Long, String>read()
                        .withBootstrapServers("main-kafka-bootstrap.kafka.svc.cluster.local:9094")
                        .withTopic("dls-elahe")
                        .withKeyDeserializer(LongDeserializer.class)
                        .withValueDeserializer(StringDeserializer.class)
                        .withConsumerConfigUpdates(ImmutableMap.of("auto.offset.reset", (Object) "earliest"))

                        // We're writing to a file, which does not support unbounded data sources. This line makes it bounded to
                        // the first 5 records.
                        // In reality, we would likely be writing to a data source that supports unbounded data, such as BigQuery.
                        .withMaxNumRecords(5)

                        .withoutMetadata() // PCollection<KV<Long, String>>
                )
                .apply(Values.<String>create())
                .apply("ExtractWords", ParDo.of(new DoFn<String, String>() {
                    @ProcessElement
                    public void processElement(ProcessContext c) {
                        for (String word : c.element().split(TOKENIZER_PATTERN)) {
                            if (!word.isEmpty()) {
                                c.output(word);
                            }
                        }
                    }
                }))
                .apply(Count.<String>perElement())
                .apply("FormatResults", MapElements.via(new SimpleFunction<KV<String, Long>, String>() {
                    @Override
                    public String apply(KV<String, Long> input) {
                        return input.getKey() + ": " + input.getValue();
                    }
                }))
                .apply(TextIO.write().to("wordcounts"));

        p.run().waitUntilFinish();
    }
}
