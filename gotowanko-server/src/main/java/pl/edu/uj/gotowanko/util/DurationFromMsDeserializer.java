package pl.edu.uj.gotowanko.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * Created by michal on 19.04.15.
 */
public class DurationFromMsDeserializer extends StdScalarDeserializer<Duration> {
    protected DurationFromMsDeserializer() {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return Duration.of(p.getLongValue(), ChronoUnit.MILLIS);
    }
}
