package safeseattle.model;

import java.sql.Timestamp;

public class Reports {
	protected String reportId;
	protected Timestamp reportTime;
	public Reports(String reportId, Timestamp reportTime) {
		this.reportId = reportId;
		this.reportTime = reportTime;
	}
	
	// creating a constructor to get report by the reportId 
	// for the purpose of finding a report by the id itself
	public Reports(String reportId) {
		this.reportId = reportId;
		
	}
	
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public Timestamp getReportTime() {
		return reportTime;
	}
	public void setReportTime(Timestamp reportTime) {
		this.reportTime = reportTime;
	}

}
