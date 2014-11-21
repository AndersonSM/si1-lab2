import java.util.Calendar;
import java.util.GregorianCalendar;


public class CalendarTestes {

	public static void main(String[] args) {
		Calendar cal = new GregorianCalendar(2014, 10, 22);
		Calendar cal2 = new GregorianCalendar(2014, 0, 1);
		Calendar cal3 = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		cal2.setFirstDayOfWeek(Calendar.SUNDAY);
		cal3.setFirstDayOfWeek(Calendar.SUNDAY);
		System.out.println(cal);
		System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
		System.out.println(cal2.get(Calendar.WEEK_OF_YEAR));
		System.out.println(cal3.get(Calendar.WEEK_OF_YEAR));
	}

}
