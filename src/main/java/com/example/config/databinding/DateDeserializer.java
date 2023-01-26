package com.example.config.databinding;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends StdDeserializer<Date> {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public DateDeserializer() {
        super(Date.class);
    }

    @Override
    public Date deserialize(final JsonParser jsonParser,
                            final DeserializationContext deserializationContext) throws IOException {
        final String dateStr = jsonParser.getText();
        try {
            return FORMATTER.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
