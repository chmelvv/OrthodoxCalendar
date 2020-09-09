package info.pomisna.OrthodoxCalendar;

import org.joda.time.DateTime;
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

    @Test
    public void convertionTest(){
        LocalDate christmasOldStyle = getOldStyleDate(2020, 01, 07);
        assertThat(christmasOldStyle.getMonthOfYear(), equalTo(12));
        assertThat(christmasOldStyle.getDayOfMonth(), equalTo(25));
    }

    @Test
    public void saintFathersWeekDayTest(){
        int year = 2021;
        LocalDate easter_prev = getEasterDateOldStyle(year-1); // Пасха в предыдущем году
        DateTime saintFathersWeekDayInPreviousYear = getSaintFathersWeekDayOldStyle(year - 1);
//        int f_e = Days.daysBetween(easter_prev, saintFathersWeekDayInPreviousYear).getDays();
//        System.out.println("Easter prev:" + easter_prev.toString());
//        System.out.println("saintFathersWeekDay prev:" + saintFathersWeekDayInPreviousYear.toString());
//        System.out.println("Days between: " + f_e);
    }
}
