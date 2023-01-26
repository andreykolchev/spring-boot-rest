package com.example.config.databinding;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerializer extends StdSerializer<Date> {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public DateSerializer() {
        super(Date.class);
    }

    @Override
    public void serialize(final Date date,
                          final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(FORMATTER.format(date));
    }

    @Override
    public Class<Date> handledType() {
        return Date.class;
    }
}
