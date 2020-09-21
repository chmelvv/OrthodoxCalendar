package info.pomisna.OrthodoxCalendar;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static info.pomisna.OrthodoxCalendar.Utils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CalendarTest {

    @Test
    public void daysBetweenOldAndNewStyleTest(){
        assertThat(daysBetweenOldAndNewStyle(2020), equalTo(13));
    }

    @Test
    public void daysBetweenOldAndNewStyle2Test(){
        assertThat(daysBetweenOldAndNewStyle2(2020), equalTo(13));
    }

    @ParameterizedTest
    @CsvSource({"2020,2,29", "2021,2,28"})
    public void numberOfDaysInMonthTest(int year, int yearMonth, int daysInMonthExpected){
        assertThat(numberOfDaysInMonth(year, yearMonth), equalTo(daysInMonthExpected));
    }

    @ParameterizedTest
    @CsvSource({"2020,259", "2021,245"})
    public void daysBetweenEasterAndSaintFathersWeekDayTest(int year, int expectedDays){
        LocalDate easter_prev = getEasterDate(year);
        LocalDate saintFathersWeekDayInPreviousYear = getSaintFathersWeekDay(year);
        assertThat(
                Days.daysBetween(easter_prev, saintFathersWeekDayInPreviousYear).getDays(),
                equalTo(expectedDays));
    }
}