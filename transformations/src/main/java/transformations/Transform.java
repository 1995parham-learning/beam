package transformations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.values.PCollection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ToString;

public class Transform extends PTransform<PCollection<String>, PCollection<String>> {

  @Override
  public PCollection<String> expand(PCollection<String> input) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'");
    df.setTimeZone(TimeZone.getTimeZone("UTC"));

    ObjectMapper mapper = JsonMapper.builder()
        .addModule(new ParameterNamesModule())
        .addModule(new Jdk8Module())
        .addModule(new JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
        .defaultDateFormat(df)
        .build();

    return input
        .apply(ParseJsons.of(DriverLocation.class).withMapper(mapper))
        .setCoder(SerializableCoder.of(DriverLocation.class))
        .apply(ToString.elements());
  }
}
