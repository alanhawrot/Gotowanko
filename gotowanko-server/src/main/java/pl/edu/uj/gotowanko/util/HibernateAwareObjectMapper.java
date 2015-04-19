package pl.edu.uj.gotowanko.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import java.time.Duration;

/**
 * Created by alanhawrot on 18.03.15.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        Module module = new SimpleModule()
                .addSerializer(new DurationToMsSerializer())
                .addDeserializer(Duration.class, new DurationFromMsDeserializer());
        registerModule(module);
        registerModule(new Hibernate4Module());
        setConfig(getSerializationConfig()
                .withSerializationInclusion(JsonInclude.Include.NON_NULL));
    }
}
