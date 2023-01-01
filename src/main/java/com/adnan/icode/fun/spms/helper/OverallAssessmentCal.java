package com.adnan.icode.fun.spms.helper;

import java.text.DecimalFormat;

import com.adnan.icode.fun.spms.entity.PercentageController;
import com.adnan.icode.fun.spms.entity.StudentSemData;

public class OverallAssessmentCal {
	
	private float internalPercent;
	
	private float previousPercent;
	
	private float classPercent;
	
	private int internalThreshold;
	
	private int previousThreshold;
	
	private int classThreshold;
	
	private int OverallThreshold;
	
	public OverallAssessmentCal() {
		
	}

	public OverallAssessmentCal(float internalPercent, float previousPercent, float classPercent, int internalThreshold,
			int previousThreshold, int classThreshold) {
		super();
		this.internalPercent = internalPercent;
		this.previousPercent = previousPercent;
		this.classPercent = classPercent;
		this.internalThreshold = internalThreshold;
		this.previousThreshold = previousThreshold;
		this.classThreshold = classThreshold;
	}

	public OverallAssessmentCal(float internalPercent, float previousPercent, float classPercent, int internalThreshold,
			int previousThreshold, int classThreshold, int overallThreshold) {
		super();
		this.internalPercent = internalPercent;
		this.previousPercent = previousPercent;
		this.classPercent = classPercent;
		this.internalThreshold = internalThreshold;
		this.previousThreshold = previousThreshold;
		this.classThreshold = classThreshold;
		OverallThreshold = overallThreshold;
	}
	
	public OverallAssessmentCal(StudentSemData tempSemData, PercentageController pController) {
		
		this.internalPercent = tempSemData.getOverallInternalAssessment();
		this.previousPercent = tempSemData.getOverallPreviousAssessment();
		this.classPercent = tempSemData.getOverallSubjectAssessment();
		
		if(pController== null) {
			this.internalThreshold = 0;
			this.previousThreshold = 0;
			this.classThreshold = 0;
		}else {
			
			this.internalThreshold = pController.getInternalThreshold();
			this.previousThreshold = pController.getPreviousThreshold();
			this.classThreshold = pController.getClassThreshold();
		}
	}

	public float overallPercent() {
		
		if(internalThreshold == 0) {
			this.internalThreshold = 100;
		}
		if(previousThreshold == 0) {
			this.previousThreshold = 100;
		}
		if(classThreshold == 0) {
			this.classThreshold = 100;
		}
		
		// calculating overall evaluation percent based on percentage controller
		// formula used:-
		//
		// (percent1 * weightage percent1 + percent2 * weightage percent2 + percentn * weightage percentn)
		//------------------------------------------------------------------------------------------------ divided by
		//                            ( weightage percent1 + weightage percent2 + weightage percentn)
		
		float overallPercent = ((internalPercent *((float)internalThreshold/100)) 
							   + (previousPercent *((float)previousThreshold/100))
							   + (classPercent    *((float)classThreshold/100))
							   ) 
											 /
							   ( ((float)internalThreshold/100) 
							   + ((float)previousThreshold/100)
							   + ((float)classThreshold/100)
							   );
		
		DecimalFormat df = new DecimalFormat("0.00");
		overallPercent = Float.parseFloat(df.format(overallPercent));
		 
		
		return overallPercent;	
	}
	
	
	
	

}
