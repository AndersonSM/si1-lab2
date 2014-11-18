import java.util.Calendar;
import java.util.GregorianCalendar;


public class CalendarTestes {

	public static void main(String[] args) {
		Calendar cal = new GregorianCalendar(2014, 0, 5);
		Calendar cal2 = new GregorianCalendar(2014, 0, 1);
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		cal2.setFirstDayOfWeek(Calendar.SUNDAY);
		System.out.println(cal);
		System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
		System.out.println(cal2.get(Calendar.WEEK_OF_YEAR));
	}

}
