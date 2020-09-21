package info.pomisna.OrthodoxCalendar;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class I18NSampleTest {

    @Test
    public void localeSupportTest() {
        Locale currentLocale = new Locale("uk", "UA");
        ResourceBundle messages = ResourceBundle.getBundle("languages\\Messages", currentLocale);
        assertThat("Easter name",
                messages.getString( "Светлое_Христово_Воскресение"),
                equalTo("Світле Христове Воскресіння"));
    }
}
