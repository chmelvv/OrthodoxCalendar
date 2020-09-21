package info.pomisna.OrthodoxCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.JulianChronology;

import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import static info.pomisna.OrthodoxCalendar.CalendarEvent.CHRISTMAS;
import static info.pomisna.OrthodoxCalendar.CalendarEvent.EPIPHANY;

public class Utils {
    static Locale currentLocale = new Locale("uk", "UA");
    static ResourceBundle messages = ResourceBundle.getBundle("languages\\Messages", currentLocale);

    // Функция определяет является ли указанный год високосным
    public static boolean ortcal_isLeap(int year){
        return Year.isLeap(year);
    }

    // Функция определяет количество дней в месяце
    public static int ortcal_numDays(int year, int month){
        DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 000);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    // Функция возвращает количество дней между датами по новому и старому стилю
    public static int ortcal_dd(int year) {
        return (year - year % 100) / 100 - (year - year % 400) / 400 - 2;
    }

    // Функция добавляет заданное количество дней к дате, заданной в виде int
    public static LocalDate ortcal_add_days(LocalDate date, int days) {
        return date.plusDays(days);
    }

    // Функция возвращает дату по старому стилю
    public static LocalDate ortcal_oldStyle(LocalDate newStyleDate) {
        return newStyleDate
                .toDateTimeAtStartOfDay()
                .withChronology(JulianChronology.getInstance())
                .toLocalDate();
    }

    // Функция возвращает дату по старому стилю
    public static LocalDate ortcal_oldStyle(int year, int month, int day) {
        LocalDate localDate = new LocalDate(JulianChronology.getInstance());
        return localDate
                .withYear(year)
                .withMonthOfYear(month)
                .withDayOfMonth(day);
    }

    public static LocalDate getNewStyleDate(LocalDate oldStyle) {
        return oldStyle
                .toDateTimeAtStartOfDay()
                .withChronology(ISOChronology.getInstance())
                .toLocalDate();
    }

    public static int daysBetweenOldAndNewStyle2(int year) {
        LocalDate newStyleDate = LocalDate.parse(String.valueOf(year));
        LocalDate tempDate = ortcal_oldStyle(newStyleDate);
        LocalDate oldStyleDate = new LocalDate(
                tempDate.getYear(),
                tempDate.getMonthOfYear(),
                tempDate.getDayOfMonth()
        );
        return Days.daysBetween(oldStyleDate, newStyleDate).getDays();
    }

    public static int numberOfDaysInMonth(int year, int month) {
        return YearMonth.of(year, month).lengthOfMonth();
    }

    // Функция определяет день Пасхи на заданный год по СТАРОМУ стилю
    public static LocalDate ortcal_easter(int y) {
        int a = (19 * (y % 19) + 15) % 30;
        int b = (2 * (y % 4) + 4 * (y % 7) + 6 * a + 6) % 7;
        int m, d;
        int f = a + b;
        if (f > 9) {
            d = f - 9;
            m = 4;
        } else {
            d = f + 22;
            m = 3;
        }
        return ortcal_oldStyle(y, m, d);
    }

    public static LocalDate getEasterDateNewStyle(int y) {
        return ortcal_easter(y).plusDays(ortcal_dd(y));
    }

    // Функция определяет название Седмицы (Недели) по годичному кругу богослужений
    public static String ortcal_sedmica(int month, int day, int year){
        LocalDate date = new LocalDate(year, month, day, JulianChronology.getInstance());
        int cd = date.getDayOfYear(); // Порядковый номер дня в году
        int ed = ortcal_easter(year).getDayOfYear();									// Порядковый номер дня пасхи в году
        int wd = date.dayOfWeek().get(); 	// Порядковый номер дня недели от 0 (воскресенье) до 6 (суббота)
        //TODO
//if ($cd < $ed-70) {				// До Недели о мытаре и фарисее идут седмицы по Пятидесятнице прошлого года
//		$ed = ortcal_easter('z', $year-1);								// Порядковый номер дня пасхи в предыдущем году
//		$nw = (int)(($cd+(ortcal_isLeap($year-1)?366:365)-($ed+49))/7)+1;
//		if ($wd == 0) return __("Неделя", 'bg_ortcal')." ".($nw-1).__("-я по Пятидесятнице", 'bg_ortcal');
//		else return __("Седмица", 'bg_ortcal')." ".$nw.__("-я по Пятидесятнице", 'bg_ortcal');
//	}
//	else if ($cd == $ed-70) return __("Неделя о мытаре и фарисее", 'bg_ortcal');	// Седмицы подготовительные
//	else if ($cd < $ed-63) return __("Седмица о мытаре и фарисее", 'bg_ortcal');
//	else if ($cd == $ed-63) return __("Неделя о блудном сыне", 'bg_ortcal');
//	else if ($cd < $ed-56) return __("Седмица о блудном сыне", 'bg_ortcal');
//	else if ($cd == $ed-56) return __("Неделя мясопустная, о Страшнем суде", 'bg_ortcal');
//	else if ($cd < $ed-49) return __("Сырная седмица (масленица)", 'bg_ortcal');
//	else if ($cd == $ed-49) return __("Неделя сыропустная. Воспоминание Адамова изгнания. Прощеное воскресенье", 'bg_ortcal');
//	else if ($cd < $ed-13) {									// Седмицы Великого поста
//		$nw = (int)(($cd - ($ed-49))/7)+1;
//		if ($wd == 0) return __("Неделя", 'bg_ortcal')." ".($nw-1).__("-я Великого поста", 'bg_ortcal');
//		else return __("Седмица", 'bg_ortcal')." ".$nw.__("-я Великого поста", 'bg_ortcal');
//	}
//	else if ($cd < $ed-7) return __("Седмица 6-я Великого поста (седмица ваий)", 'bg_ortcal');
//	else if ($cd == $ed-7) return __("Неделя 6-я Великого поста ваий (цветоносная, Вербное воскресенье)", 'bg_ortcal');
//	else if ($cd < $ed) return __("Страстная седмица", 'bg_ortcal');
//	else if ($cd == $ed) return "";
//	else if ($cd < $ed+7) return __("Пасхальная (Светлая) седмица", 'bg_ortcal');
//	else if ($cd < $ed+50) {									// Седмицы по Пасхе
//		$nw = (int)(($cd - $ed)/7)+1;
//		if ($wd == 0) return __("Неделя", 'bg_ortcal')." ".$nw.__("-я по Пасхе", 'bg_ortcal');
//		else return __("Седмица", 'bg_ortcal')." ".$nw.__("-я по Пасхе", 'bg_ortcal');
//	}
//	else  {														// Седмицы по Пятидесятнице
//		$nw = (int)(($cd - ($ed+49))/7)+1;
//		if ($wd == 0) return __("Неделя", 'bg_ortcal')." ".($nw-1).__("-я по Пятидесятнице", 'bg_ortcal');
//		else {
//			if ($nw==1) return __("Седмица 1-я по Пятидесятнице (Троицкая)", 'bg_ortcal');
//			else return __("Седмица", 'bg_ortcal')." ".$nw.__("-я по Пятидесятнице", 'bg_ortcal');
//		}
//	}

        return "";
    }

    public static Long getEasterDateDiff(LocalDate date) {
        long easterDiff = date.getDayOfYear() - ortcal_easter(date.getYear()).getDayOfYear();
        if (easterDiff < -104) {
            easterDiff = Days.daysBetween(ortcal_easter(date.minusYears(1).getYear()), date).getDays();
        }
        return easterDiff;
    }

    public static LocalDate getSundayBeforeDate(int year, CalendarEvent calendarEvent){
        DateTime dateTime = new DateTime(JulianChronology.getInstance());
        dateTime = dateTime.withYear(year).withMonthOfYear(calendarEvent.getMonth()).withDayOfMonth(calendarEvent.getDay()); //set Christmas day
        return dateTime.minusDays(dateTime.getDayOfWeek()).toLocalDate();
    }
    public static LocalDate getSaintFathersWeekDay(int year) {
        //Sunday before Christmas
        return getSundayBeforeDate(year, CHRISTMAS);
    }

    public static LocalDate getSundayBeforeEpiphany(int year){
        return getSundayBeforeDate(year, EPIPHANY);
    }

    public static void dayEvents(int year, int month, int day) {
        // Дата по старому стилю
        LocalDate date = new LocalDate(year, month, day, JulianChronology.getInstance());
        LocalDate easter = ortcal_easter(year); // Пасха в текущем году
        int os_year = date.getYear(); // Год по старому стилю
        int dd = ortcal_dd(os_year); // Отклонение григорианского календаря от юлианского в днях
        boolean leap = Year.isLeap(date.getYear()); // true - если високосный год по старому стилю
        LocalDate ny = new LocalDate(year, 1, 1);  // Новый год по григорианскому календарю

        LocalDate easter_prev = ortcal_easter(year - 1); // Пасха в предыдущем году
        int f_e = Days.daysBetween(getSaintFathersWeekDay(year-1), easter_prev).getDays(); // Кол-во дней до Недели св.отцов от Пасхи
        int ep_e = Days.daysBetween(easter, getSundayBeforeEpiphany(year)).getDays(); // Кол-во дней до Недели перед Богоявлением от Пасхи
        int wd = date.getDayOfWeek();
//ResourceBundle messages = ResourceBundle.getBundle("languages\\Messages", currentLocale);
        String post = (wd == 3 || wd == 5) ? messages.getString("Постный_день") : ""; // Пост по средам и пятницам
        String noglans = (wd == 2 || wd == 4 || wd == 6) ? messages.getString("Браковенчание_не_совершается") : "";	// Браковенчание не совершается накануне среды и пятницы всего года (вторник и четверг), и воскресных дней (суббота)
        HashMap<String, String> result = new HashMap<>();

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
}
