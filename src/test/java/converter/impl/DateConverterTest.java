package converter.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

class DateConverterTest {
    private DateConverter dateConverter;
    private Calendar calendar;

    @BeforeEach
    public void resetDateConverter() {
        this.dateConverter = new DateConverter();
        this.calendar = Calendar.getInstance();
    }

    @Test
    public void checkDateToLocalDateConversion() {
        Function<Date, LocalDate> dateFunction = date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dateConverter.addRule(LocalDate.class, dateFunction);

        this.calendar.set(2020, Calendar.OCTOBER, 1);
        Date date = this.calendar.getTime();
        LocalDate expectedLocalDate = dateFunction.apply(date);

        Assertions.assertEquals(this.calendar.getTime(), date);
        Assertions.assertEquals(expectedLocalDate, dateConverter.convert(date, LocalDate.class));
    }

    @Test
    public void checkDateToLocalDateTimeConversion() {
        Function<Date, LocalDateTime> dateFunction = date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        dateConverter.addRule(LocalDateTime.class, dateFunction);

        this.calendar.set(2020, Calendar.OCTOBER, 1, 10, 30, 0);
        Date date = this.calendar.getTime();
        LocalDateTime expectedLocalDateTime = dateFunction.apply(date);

        Assertions.assertEquals(this.calendar.getTime(), date);
        Assertions.assertEquals(expectedLocalDateTime, dateConverter.convert(date, LocalDateTime.class));
    }

    @Test
    public void checkDateToStringConversion() {
        Function<Date, String> dateFunction = Date::toString;
        dateConverter.addRule(String.class, dateFunction);

        this.calendar.set(2020, Calendar.OCTOBER, 1, 10, 30, 0);
        Date date = this.calendar.getTime();
        String expectedString = dateFunction.apply(date);

        Assertions.assertEquals(this.calendar.getTime(), date);
        Assertions.assertEquals(expectedString, dateConverter.convert(date, String.class));
    }

    @Test
    public void checkConversionException() {
        Function<Date, String> dateFunction = Date::toString;
        dateConverter.addRule(String.class, dateFunction);

        this.calendar.set(2020, Calendar.OCTOBER, 1, 10, 30, 0);
        Date date = this.calendar.getTime();
        String expectedString = dateFunction.apply(date);
        String expectedMessage = String.format("Can not convert object from %s", date.getClass());

        Assertions.assertEquals(this.calendar.getTime(), date);
        Assertions.assertEquals(expectedString, dateConverter.convert(date, String.class));
        Assertions.assertThrows(IllegalArgumentException.class, () -> dateConverter.convert(date, Integer.class), expectedMessage);
    }
}