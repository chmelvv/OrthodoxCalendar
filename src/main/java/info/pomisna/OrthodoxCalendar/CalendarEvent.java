package info.pomisna.OrthodoxCalendar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CalendarEvent {
    CHRISTMAS(12, 25),
    EPIPHANY(1, 6);

    int month;
    int day;
}
