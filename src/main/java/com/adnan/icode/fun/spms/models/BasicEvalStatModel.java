package com.adnan.icode.fun.spms.models;

public class BasicEvalStatModel {
	
	private int semester;
	
	private int internalStat;
	
	private int previousStat;
	
	private int classPerStat;
	
	private float overallStat;
	
	private String iMsg;
	
	private String pMsg;
	
	private String cMsg;	
	

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getInternalStat() {
		return internalStat;
	}

	public void setInternalStat(int internalStat) {
		this.internalStat = internalStat;
	}

	public int getPreviousStat() {
		return previousStat;
	}

	public void setPreviousStat(int previousStat) {
		this.previousStat = previousStat;
	}

	public int getClassPerStat() {
		return classPerStat;
	}

	public void setClassPerStat(int classPerStat) {
		this.classPerStat = classPerStat;
	}

	public float getOverallStat() {
		return overallStat;
	}

	public void setOverallStat(float overallStat) {
		this.overallStat = overallStat;
	}

	public String getiMsg() {
		return iMsg;
	}

	public void setiMsg(String iMsg) {
		this.iMsg = iMsg;
	}

	public String getpMsg() {
		return pMsg;
	}

	public void setpMsg(String pMsg) {
		this.pMsg = pMsg;
	}

	public String getcMsg() {
		return cMsg;
	}

	public void setcMsg(String cMsg) {
		this.cMsg = cMsg;
	}
	
	
	

}
