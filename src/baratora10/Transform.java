package baratora10;

import java.sql.Date;
import java.util.Calendar;

public class Transform {

	public Calendar getCal(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public Date getDate(Calendar cal) {
		Date date = new Date(cal.getTime().getTime());
		return date;
	}

}
