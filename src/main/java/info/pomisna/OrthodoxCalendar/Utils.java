package info.pomisna.OrthodoxCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.chrono.JulianChronology;

import java.time.Year;
import java.time.YearMonth;

public class Utils {
    public static int daysBetweenOldAndNewStyle(int year){
        return (year - year%100)/100 - (year - year%400)/400 - 2;
    }

    public static LocalDate getOldStyleDate(LocalDate newStyleDate){
        return newStyleDate.toDateTimeAtStartOfDay().withChronology(JulianChronology.getInstance()).toLocalDate();
    }
//
//    public static LocalDate getNewStyleDate(LocalDate oldStyleDate){
//        LocalDate localDate = new LocalDate();
//        return localDate
//                .withYear(oldStyleDate.getYear())
//                .withMonthOfYear(oldStyleDate.getMonthOfYear())
//                .withDayOfMonth(oldStyleDate.getDayOfMonth());
//    }
//
    public static LocalDate getOldStyleDate(int year, int month, int day){
        LocalDate localDate = new LocalDate(JulianChronology.getInstance());
        return localDate
                .withYear(year)
                .withMonthOfYear(month)
                .withDayOfMonth(day);
    }
//
//    public static LocalDate getNewStyleDate(int year, int month, int day){
//        LocalDate localDate = new LocalDate();
//        return localDate
//                .withYear(year)
//                .withMonthOfYear(month)
//                .withDayOfMonth(day);
//    }
//
//    public static int daysBetweenOldAndNewStyle2(int year){
//        LocalDate newStyleDate = LocalDate.parse(String.valueOf(year));
//        LocalDate tempDate = getOldStyleDate(newStyleDate);
//        LocalDate oldStyleDate = new LocalDate(
//                tempDate.getYear(),
//                tempDate.getMonthOfYear(),
//                tempDate.getDayOfMonth()
//        );
//    return Days.daysBetween(oldStyleDate, newStyleDate).getDays();
//    }

    public static int numberOfDaysInMonth(int year, int month){
        return YearMonth.of(year, month).lengthOfMonth();
    }

    public static LocalDate getEasterDateOldStyle(int y){
        int a = (19*(y%19) + 15)%30;
        int b = (2*(y%4) + 4*(y%7) + 6*a +6)%7;
        int m, d;
        int f = a + b;
        if (f > 9) {
            d = f - 9;
            m = 4;
        } else {
            d = f + 22;
            m = 3;
        }
        return getOldStyleDate(y, m, d);
    }

    public static LocalDate getEasterDate(int y){
        return getEasterDateOldStyle(y).plusDays(daysBetweenOldAndNewStyle(y));
    }

    public static Long getEasterDateDiff(LocalDate date){
        long easterDiff = date.getDayOfYear() - getEasterDate(date.getYear()).getDayOfYear();
        if (easterDiff < -104) {
            easterDiff = Days.daysBetween(getEasterDate(date.minusYears(1).getYear()), date).getDays();
        }
        return easterDiff;
    }

    public static DateTime getSaintFathersWeekDayOldStyle(int year){
        DateTime dateTime = new DateTime(JulianChronology.getInstance());
        dateTime = dateTime.withMonthOfYear(12).withDayOfMonth(25); //set Christmas day
        return dateTime.minusDays(dateTime.getDayOfWeek());
    }

    public static void dayEvents(int year, int month, int day){
        LocalDate newStyleDate = new LocalDate(year, month, day);
        LocalDate date = getOldStyleDate(newStyleDate);
//        // Дата по старому стилю
        int os_year = date.getYear();								// Год по старому стилю
        int dd = daysBetweenOldAndNewStyle(os_year);										// Отклонение григорианского календаря от юлианского в днях
        boolean leap = Year.isLeap(date.getYear());         // true - если високосный год по старому стилю
        LocalDate ny =  new LocalDate(year, 1, 1);  // Новый год по григорианскому календарю
        LocalDate easter = getEasterDate(year);						// Пасха в текущем году
        LocalDate easter_prev = getEasterDate(year-1); // Пасха в предыдущем году
//
//
//        int f_e = Days.daysBetween(getSaintFathersWeekDayOldStyle(year-1), easter_prev).getDays();
////                - new LocalDate(year-1, 25 + daysBetweenOldAndNewStyle(year-1), 12).getWeekyear()
////$f_e = (date( 'U', mktime ( 0, 0, 0,12, 25, $year-1 ))-$easter_prev)/DAY_IN_SECONDS
//// -date( 'w', mktime ( 0, 0, 0, 12, 25+ortcal_dd($year-1), $year-1 )); // Кол-во дней до Недели св.отцов от Пасхи
//        //
//                //date( 'U', mktime ( 0, 0, 0,12, 25, $year-1 ))-$easter_prev)/DAY_IN_SECONDS -
//                //date( 'w', mktime ( 0, 0, 0, 12, 25+ortcal_dd($year-1), $year-1 )); // Кол-во дней до Недели св.отцов от Пасхи
////        mktime ([ int $hour = date("H")
////                [, int $minute = date("i")
////                [, int $second = date("s")
////                [, int $month = date("n")
////                [, int $day = date("j")
////                [, int $year = date("Y")
////                [, int $isDST = -1 ]]]]]]] )
////        $ep_e = (date( 'U', mktime ( 0, 0, 0, 1, 6, $year ))-$easter_prev)/DAY_IN_SECONDS-date( 'w', mktime ( 0, 0, 0, 1, 6+ortcal_dd($year), $year )); 		// Кол-во дней до Недели перед Богоявлением от Пасхи
////        $wd = date( 'w', mktime ( 0, 0, 0, $month, $day, $year ));
////        if ($wd == 3 || $wd == 5) $post = __("Постный день", 'bg_ortcal');				// Пост по средам и пятницам
////        else $post = "";
////        if ($wd == 2 || $wd == 4 || $wd == 6) $noglans = __("Браковенчание не совершается", 'bg_ortcal');	// Браковенчание не совершается накануне среды и пятницы всего года (вторник и четверг), и воскресных дней (суббота)
////        else $noglans = "";
////        $result = array();
////        $cnt = count($events);
////        $y = 0;
////        if ($cnt) {
////            for ($i=0; $i < $cnt; $i++) {
////                $event = $events[$i];
////                $s_date = (int) $event["s_date"];
////                $s_month = (int) $event["s_month"];
////                $f_date = (int) $event["f_date"];
////                $f_month = (int) $event["f_month"];
////                $name = $event["name"];
////                $link = $event["link"];
////                $type = (int) $event["type"];
////                $discription = $event["discription"];
////
////                // Если невисокосный год, то события которые приходятся на 29 февраля празднуются 28 февраля
////                if ( ! $leap ) {
////                    if ( $s_month == 2 && $s_date == 29 ) {
////                        $s_date == 28;
////                    }
////                    if ( $f_month == 2 && $f_date == 29 ) {
////                        $f_date == 28;
////                    }
////                }
////
////                if ( $s_month < 0 ) {        //  Сб./Вс. перед/после праздника или Праздник в Сб./Вс. перед/после даты
////                    // Если неделя Богоотцов совпадает с Неделей перед Богоявлением, то чтения Недели перед Богоявлением переносятся на 1 января ст.ст.
////                    if ( ( $f_e + 7 == $ep_e ) && ( $s_month == - 1 && $s_date == 0 && $f_month == 1 && $f_date == 6 && ( $type == 204 || $type == 207 ) ) ) {
////                        $finish = date( 'U', mktime( 0, 0, 0, 1, 1, $os_year ) );
////                        $start  = $finish;
////                    } else {
////                        if ($s_month == -4) $f_date = $f_date - 3;
////                        $we     = date( 'w', mktime( 0, 0, 0, $f_month, $f_date+$dd, $os_year + $y ) );				// День недели
////                        $finish = date( 'U', mktime( 0, 0, 0, $f_month, $f_date+($s_date-$we), $os_year + $y ) );	// Смещение относительно даты на $s_date-$we дней
////                        $start  = $finish;
////
////                        if ( $s_month == - 1 && $we == $s_date ) {		// Если Сб./Вс. приходится на самый день праздника, то не отмечается
////                            $name = "";
////                        }
////                        if ( $s_month == - 3 && $we != $s_date ) {		// Если праздник не совпадает с указанным днем недели, то не отмечается
////                            $name = "";
////                        }
////                        if ( $y == 0 ) {								// Проверяем дважды: для текущего и следующего года
////                            $i --;
////                            $y = 1;
////                        }
////                        else {
////                            $y = 0;
////                        }
////                    }
////                } else {
////                    if ( $s_month > 0 ) {						// Неподвижные события - начало периода
////                        $start = date( 'U', mktime( 0, 0, 0, $s_month, $s_date, $os_year ) );
////                    }
////                    else if ( $s_month == 0 ) {					// Переходящие события - начало периода
////                        if ( $type == 202 ) {						// Чтения на утрени
////                            $start = ortcal_shift202( $s_date, $date, $os_year );
////                        }
////                        else if ( $type == 204 ) {					// Апостол на Литургии
////                            $start = ortcal_shift204( $s_date, $date, $os_year );
////                        }
////                        else if ( $type == 207 ) {					// Евангелие на Литургии
////                            $start = ortcal_shift207( $s_date, $date, $os_year );
////                        }
////                        else if ( $type >= 301 && $type <= 309 ) {	// Псалтирь
////                            $start = ortcal_shift300( $s_date, $date, $os_year );
////                        }
////                        else {										// Все остальные события
////                            if ( $date >= $ny ) {						// После Нового года - отсчет от текущей Пасхи
////                                $start = ortcal_add_days($easter, $s_date);
////                            }
////                            else {										// До Нового года - отсчет от предыдущей Пасхи
////                                $start = ortcal_add_days($easter_prev, $s_date);
////                            }
////                        }
////                    }
////
////                    if ( $f_month > 0 ) {						// Неподвижные события - конец периода
////                        $finish = date( 'U', mktime( 0, 0, 0, $f_month, $f_date, $os_year ) );
////                    }
////                    else if ( $f_month == 0 ) {						// Переходящие события - конец периода
////                        if ( $type == 202 ) {						// Чтения на утрени
////                            $finish = ortcal_shift202( $s_date, $date, $os_year );
////                        }
////                        else if ( $type == 204 ) {					// Апостол на Литургии
////                            $finish = ortcal_shift204( $s_date, $date, $os_year );
////                        }
////                        else if ( $type == 207 ) {					// Евангелие на Литургии
////                            $finish = ortcal_shift207( $s_date, $date, $os_year );
////                        }
////                        else if ( $type >= 301 && $type <= 309 ) {	// Псалтирь
////                            $finish = ortcal_shift300( $s_date, $date, $os_year );
////                        }
////                        else {										// Все остальные события
////                            if ( $date >= $ny ) {						// После Нового года - отсчет от текущей Пасхи
////                                $finish = ortcal_add_days($easter, $f_date);
////                            }
////                            else {										// До Нового года - отсчет от предыдущей Пасхи
////                                $finish = ortcal_add_days($easter_prev, $f_date);
////                            }
////                        }
////                    }
////                }
////
////                // Обрабатываем коллизию, связанную со сменой года
////                if ($start > $finish) {
////                    if ($start > $date) $start = ortcal_add_days($start, ($leap?(-366):(-365)));	// Начало в прошлом году
////                    else $finish = ortcal_add_days($finish, ($leap?(366):(365)));					// Окончание в следующем году
////                }
////
////                if ($start && $finish) {
////                    // Событие относится к данному дню, если
////                    // - его наименование не пустое,
////                    // - день попадает в интервал между начальной и конечной датой события
////                    if ($name != "" && $date >= $start && $date <= $finish) {
////                        $s = ortcal_add_days($start, $dd);
////                        $f = ortcal_add_days($finish, $dd);
////                        $result[] = array (
////                                "s_date" => date ("d", $s),
////                                "s_month" => date ("m", $s),
////                                "s_year" => date ("Y", $s),
////                                "f_date" => date ("d", $f),
////                                "f_month" => date ("m", $f),
////                                "f_year" => date ("Y", $f),
////                                "name" => $name,
////                                "type" => $event["type"],
////                                "link" => $event["link"],
////                                "discription" => $event["discription"]);
////                        if ($event["type"] == '10' || $event["type"] == '100') $post = "";	// Уже установлен пост по другой причине или сплошная седмица
////                        if ($event["type"] == '20') $noglans = "";							// Уже запрещено браковенчание по другой причине
////                    }
////                }
////            }
////        }
////        if ($post != "") {													// Пост по средам и пятницам
////            $result[] = array (	"s_date" => $day,
////                    "s_month" => $month,
////                    "s_year" => $year,
////                    "f_date" => $day,
////                    "f_month" => $month,
////                    "f_year" => $year,
////                    "name" => $post,
////                    "type" => "10",
////                    "link" => "",
////                    "discription" => "");
////        }
////        if ($noglans != "") {												// Браковенчание не совершается по вторникам, четвергам и субботам
////            $result[] = array (	"s_date" => $day,
////                    "s_month" => $month,
////                    "s_year" => $year,
////                    "f_date" => $day,
////                    "f_month" => $month,
////                    "f_year" => $year,
////                    "name" => $noglans,
////                    "type" => "20",
////                    "link" => "",
////                    "discription" => "");
////        }
////
////        set_transient( $key, $result, YEAR_IN_SECONDS );
//////error_log(''.(microtime(true)-$start_time).' сек.(!!! '.$key.')'.PHP_EOL, 3, dirname(__FILE__)."/bg_error.log" );
////    }
//////error_log(''.(microtime(true)-$start_time).' сек.('.$key.')'.PHP_EOL, 3, dirname(__FILE__)."/bg_error.log" );
////	return $result;
//    }
}
