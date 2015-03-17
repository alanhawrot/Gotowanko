package pl.gotowanko.android.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateFormat {
	public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String timeFormat = "HH:MM";
	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat, Locale.getDefault());
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());

	public static Date formatTime(String s) throws ParseException {
		return timeFormatter.parse(s);
	}

	public static String formatTime(Date date) {
		return timeFormatter.format(date);
	}

	public static Date formatDateTime(String s) throws ParseException {
		return dateFormatter.parse(s);
	}

	public static String formatDateTime(Date date) {
		return dateFormatter.format(date);
	}
}
