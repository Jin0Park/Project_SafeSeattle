package safeseattle.model;

import java.sql.Timestamp;

public class LoginInformation {
	protected int loginId;
	protected Persons person;
	protected Timestamp sessionStart;
	protected userOrAdmin userOrAdmin;
	public enum userOrAdmin {
		admin, user
	}
	
	
	public LoginInformation(int loginId, Persons person, Timestamp sessionStart, userOrAdmin userOrAdmin) {
		this.loginId = loginId;
		this.person = person;
		this.sessionStart = sessionStart;
		this.userOrAdmin = userOrAdmin;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public Persons getPerson() {
		return person;
	}

	public void setPerson(Persons person) {
		this.person = person;
	}

	public Timestamp getSessionStart() {
		return sessionStart;
	}

	public void setSessionStart(Timestamp sessionStart) {
		this.sessionStart = sessionStart;
	}

	public userOrAdmin getUserOrAdmin() {
		return userOrAdmin;
	}

	public void setUserOrAdmin(userOrAdmin userOrAdmin) {
		this.userOrAdmin = userOrAdmin;
	}
}
