package safeseattle.model;

import java.sql.Date;

public class Offenses {
	protected long offenseId;
	protected String reportId;
	protected Date offenseDate;
	protected String offenseCode;
	protected String blockAddress;
	protected String mcpp;
	protected double longitude;
	protected double latitude;
	
	public Offenses(long offenseId, String reportId, Date offenseDate, String offenseCode,
					String blockAddress,String mcpp, double longitude, double latitude) {
		this.offenseId = offenseId;
		this.reportId = reportId;
		this.offenseDate = offenseDate;
		this.offenseCode = offenseCode;
		this.blockAddress = blockAddress;
		this.mcpp = mcpp;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Offenses (String reportId, Date offenseDate, String offenseCode,
					String blockAddress,String mcpp, double longitude, double latitude) {
		this.reportId = reportId;
		this.offenseDate = offenseDate;
		this.offenseCode = offenseCode;
		this.blockAddress = blockAddress;
		this.mcpp = mcpp;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public long getOffenseId() {
		return offenseId;
	}

	public void setOffenseId(long offenseId) {
		this.offenseId = offenseId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public Date getOffenseDate() {
		return offenseDate;
	}

	public void setOffenseDate(Date offenseDate) {
		this.offenseDate = offenseDate;
	}

	public String getOffenseCode() {
		return offenseCode;
	}

	public void setOffenseCode(String offenseCode) {
		this.offenseCode = offenseCode;
	}

	public String getBlockAddress() {
		return blockAddress;
	}

	public void setBlockAddress(String blockAddress) {
		this.blockAddress = blockAddress;
	}

	public String getMcpp() {
		return mcpp;
	}

	public void setMcpp(String mcpp) {
		this.mcpp = mcpp;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}
