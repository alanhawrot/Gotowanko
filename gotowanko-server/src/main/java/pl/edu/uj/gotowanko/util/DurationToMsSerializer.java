package pl.edu.uj.gotowanko.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.time.Duration;

/**
 * Created by michal on 19.04.15.
 */
public class DurationToMsSerializer extends StdScalarSerializer<Duration> {
    protected DurationToMsSerializer() {
        super(Duration.class);
    }

    @Override
    public void serialize(Duration value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (value != null)
            jgen.writeNumber(value.toMillis());
    }
}
