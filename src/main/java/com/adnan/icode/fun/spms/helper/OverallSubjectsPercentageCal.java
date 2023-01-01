package com.adnan.icode.fun.spms.helper;

import java.text.DecimalFormat;
import java.util.List;
import com.adnan.icode.fun.spms.entity.StudentPerSubject;

public class OverallSubjectsPercentageCal {
	
	public float calculate(List<StudentPerSubject> allFilledSubjects) {
		
		float overllpercentage = 0;
		
		for(StudentPerSubject oneFilledSub : allFilledSubjects) {
			overllpercentage = overllpercentage + oneFilledSub.getSubjectOverall();
		}
		
		overllpercentage = overllpercentage/allFilledSubjects.size();
		
		DecimalFormat df = new DecimalFormat("0.00");
		overllpercentage = Float.parseFloat(df.format(overllpercentage));
		
		return overllpercentage;
	}
	


}
