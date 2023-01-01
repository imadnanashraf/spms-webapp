package com.adnan.icode.fun.spms.helper;



import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateAdder {
	
	public Date addHoursToDate(Date currentDate, int minutes) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MINUTE, minutes);
		
		return calendar.getTime();
		
	}

}
