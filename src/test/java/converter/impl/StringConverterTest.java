package converter.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

class StringConverterTest {
    private StringConverter stringConverter;

    @BeforeEach
    public void resetDateConverter() {
        this.stringConverter = new StringConverter();
    }

    @Test
    public void checkStringToLocalDateConversion() {
        Function<String, LocalDate> stringFunction = LocalDate::parse;
        stringConverter.addRule(LocalDate.class, stringFunction);

        String string = "2020-10-01";
        LocalDate expectedLocalDate = stringFunction.apply(string);

        Assertions.assertEquals(expectedLocalDate, stringConverter.convert(string, LocalDate.class));
    }

    @Test
    public void checkStringToLocalDateTimeConversion() {
        Function<String, LocalDateTime> stringFunction = LocalDateTime::parse;
        stringConverter.addRule(LocalDateTime.class, stringFunction);

        String string = "2020-10-01T10:30:00";
        LocalDateTime expectedLocalDateTime = stringFunction.apply(string);

        Assertions.assertEquals(expectedLocalDateTime, stringConverter.convert(string, LocalDateTime.class));
    }

    @Test
    public void checkStringToLongConversion() {
        Function<String, Long> stringFunction = Long::parseLong;
        stringConverter.addRule(Long.class, stringFunction);

        String string = "123456789";
        Long expectedLong = stringFunction.apply(string);

        Assertions.assertEquals(expectedLong, stringConverter.convert(string, Long.class));
    }

    @Test
    public void checkConversionException() {
        Function<String, Integer> stringFunction = Integer::parseInt;
        stringConverter.addRule(Integer.class, stringFunction);

        String string = "1";
        Long ln = Long.parseLong(string);
        Integer expectedInteger = stringFunction.apply(string);
        String expectedMessage = String.format("Can not convert object from %s", ln.getClass());

        Assertions.assertEquals(expectedInteger, stringConverter.convert(string, Integer.class));
        Assertions.assertThrows(IllegalArgumentException.class, () -> stringConverter.convert(string, Long.class), expectedMessage);
    }
}