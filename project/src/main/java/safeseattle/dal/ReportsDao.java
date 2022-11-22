package safeseattle.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import safeseattle.model.Reports;

public class ReportsDao {
	protected ConnectionManager connectionManager;
	
	private static ReportsDao instance = null;
	
	protected ReportsDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static ReportsDao getinstance() {
		if (instance == null) {
			instance = new ReportsDao();
		}
		return instance;
	}
	
	
	public Reports create(Reports report) throws SQLException {
		String insertReport =
			"INSERT INTO Reports(reportId, reportTime) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReport,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, report.getReportId());
			insertStmt.setTimestamp(2,  report.getReportTime());
			insertStmt.executeUpdate();
			
		
			return report;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		
		}
	}
	
	
	/**
	 * Get the ReportId record by fetching it from your MySQL instance.
	 * This runs a SELECT/READ statement and returns a single Report instance.
	 */
	public Reports getReportById(String reportId) throws SQLException {
		String selectComment =
			"SELECT ReportId, ReportTime " +
			"FROM Reports " +
			"WHERE ReportId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setString(1, reportId);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String resultReportId = results.getString("reportId");
				Timestamp reportTime = results.getTimestamp("ReportTime");
				Reports report = new Reports(resultReportId, reportTime);
				return report;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	
	/**
	 * Delete the Reports instance
	 * This runs a DELETE statement.
	 */
	public Reports delete(Reports report) throws SQLException {
		String deleteReport = "DELETE FROM Reports WHERE ReportId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReport);
			deleteStmt.setString(1, report.getReportId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogComments instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
}
	
	
	
	


