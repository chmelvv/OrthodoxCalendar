package info.pomisna.OrthodoxCalendar;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static info.pomisna.OrthodoxCalendar.Utils.*;
import static info.pomisna.OrthodoxCalendar.Utils.ortcal_sedmica;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CalendarTest {

    @Test
    public void daysBetweenOldAndNewStyleTest(){
        assertThat(ortcal_dd(2020), equalTo(13));
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
        LocalDate easter_prev = ortcal_easter(year);
        LocalDate saintFathersWeekDayInPreviousYear = getSaintFathersWeekDay(year);
        assertThat(
                Days.daysBetween(easter_prev, saintFathersWeekDayInPreviousYear).getDays(),
                equalTo(expectedDays));
    }

    @ParameterizedTest
    @CsvSource({"2020,2,29", "2019,3,31"})
    public void ortcalNumDaysTEst(int year, int month, int expectedDaysInMonth){
        assertThat("Is in year:" + year + ", month:" + month + " has " + expectedDaysInMonth + " days?",
                ortcal_numDays(year, month),
                equalTo(expectedDaysInMonth));
    }

    @ParameterizedTest
    @CsvSource({"2020-02-29,1,2020-03-01", "2019-05-01,5,2019-05-06"})
    public void  addDaysTest(LocalDate date, int days, LocalDate expectedDate){
        assertThat(
                ortcal_add_days(date, days),
                equalTo(expectedDate));
    }

    @Test
    void sedmicaTest() {
        LocalDate startDate = LocalDate.parse("2020-01-01");
        LocalDate endDate = LocalDate.parse("2021-01-01");
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
        {
            System.out.println(date.toString() + ": " + ortcal_sedmica(date.getMonthOfYear(), date.getDayOfMonth(), 2020));
        }
        //assertThat(ortcal_sedmica(12,24,2019), equalTo(""));
    }

}