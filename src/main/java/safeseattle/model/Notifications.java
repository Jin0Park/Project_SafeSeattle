package safeseattle.model;

import java.sql.Timestamp;

public class Notifications {
	protected int notificationId;
	protected Users user;
	protected String message;
	protected Timestamp notificationTime;
	protected Reports report;

	
    public Notifications(int notificationId, Users user, String message, Timestamp notificationTime, Reports report) {
    	this.notificationId = notificationId;
    	this.user = user;
    	this.message = message;
    	this.notificationTime = notificationTime;
    	this.report = report;
    }

    public Notifications(Users user, String message, Timestamp notificationTime, Reports report) {
    	this.user = user;
    	this.message = message;
    	this.notificationTime = notificationTime;
    	this.report = report;
    }

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(Timestamp notificationTime) {
		this.notificationTime = notificationTime;
	}

	public Reports getReport() {
		return report;
	}

	public void setReport(Reports report) {
		this.report = report;
	}
}
