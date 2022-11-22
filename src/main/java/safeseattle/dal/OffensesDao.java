package safeseattle.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import safeseattle.model.*;

public class OffensesDao {
	protected ConnectionManager connectionManager;
	
	private static OffensesDao instance = null;
	
	protected OffensesDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static OffensesDao getInstance() {
		if (instance == null) {
			instance = new OffensesDao();
		}
		return instance;
	}
	
	public Offenses create(Offenses offense) throws SQLException {
		String insertOffense = 
			"INSERT INTO Offenses(OffenseId, ReportId, OffenseDate, OffenseCode, BlockAddress, MCPP, Longitude, Latitude) " +
			"VALUES(?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		//ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertOffense, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setLong(1, offense.getOffenseId());
			insertStmt.setString(2, offense.getReportId());
			insertStmt.setDate(3, offense.getOffenseDate());
			insertStmt.setString(4, offense.getOffenseCode());
			insertStmt.setString(5, offense.getBlockAddress());
			insertStmt.setString(6, offense.getMcpp());
			insertStmt.setDouble(7, offense.getLongitude());
			insertStmt.setDouble(8, offense.getLatitude());
			
			insertStmt.executeUpdate();
			/*
			resultKey = insertStmt.getGeneratedKeys();
			int offenseId = -1;
			if (resultKey.next()) {
				offenseId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			offense.setOffenseId(offenseId);*/
			return offense;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	
	public Offenses getOffenseById(long offenseId) throws SQLException {
		String selectOffense = 
			"SELECT OffenseId, ReportId, OffenseDate, OffenseCode, BlockAddress, MCPP, Longitude, Latitude " +
			"FROM Offenses " +
			"WHERE OffenseId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectOffense);
			selectStmt.setLong(1, offenseId);
			results = selectStmt.executeQuery();
			
			if (results.next()) {
				long offenseID = results.getLong("offenseId");
				String reportId = results.getString("reportId");
				Date offenseDate = results.getDate("offenseDate");
				String offenseCode = results.getString("offenseCode");
				String blockAddress = results.getString("blockAddress");
				String mcpp = results.getString("mcpp");
				double longitude = results.getDouble("longitude");
				double latitude = results.getDouble("latitude");
				
				Offenses offense = new Offenses(offenseID, reportId, offenseDate, offenseCode,
												blockAddress, mcpp, longitude, latitude);
				return offense;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
		}
		return null;
	}
	
	public List<Offenses> getOffensesByReportId(String reportID) throws SQLException {
		List<Offenses> offensesList = new ArrayList<Offenses>();
		String selectOffenses =
				"SELECT OffenseId, ReportId, OffenseDate, OffenseCode, BlockAddress, MCPP, Longitude, Latitude " +
				"FROM Offenses " +
				"WHERE ReportId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectOffenses);
			selectStmt.setString(1, reportID);
			results = selectStmt.executeQuery();
			while (results.next()) {
				long offenseId = results.getLong("offenseId");
				String reportId = results.getString("reportId");
				Date  offenseDate = results.getDate("offenseDate");
				String offenseCode = results.getString("offenseCode");
				String blockAddress = results.getString("blockAddress");
				String mcpp = results.getString("mcpp");
				double longitude = results.getDouble("longitude");
				double latitude = results.getDouble("latitude");
				
				Offenses offense = new Offenses(offenseId, reportId, offenseDate, offenseCode,
					blockAddress, mcpp, longitude, latitude);
				offensesList.add(offense);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally  {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return offensesList;
	}
	
	public Offenses delete(Offenses offense) throws SQLException {
		String deleteOffense = "DELETE FROM Offenses WHERE OffenseId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteOffense);
			deleteStmt.setLong(1, offense.getOffenseId());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
}

























