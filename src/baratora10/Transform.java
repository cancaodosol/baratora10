package baratora10;

import java.sql.Date;
import java.util.Calendar;

public class Transform {

	Calendar getCal(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	Date getDate(Calendar cal) {
		Date date = new Date(cal.getTime().getTime());
		return date;
	}

}
