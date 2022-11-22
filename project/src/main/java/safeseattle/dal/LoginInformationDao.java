package safeseattle.dal;

import safeseattle.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LoginInformationDao {
    protected ConnectionManager connectionManager;
	protected PersonsDao personsDao = PersonsDao.getInstance();
    private static LoginInformationDao instance = null;

    protected LoginInformationDao() {
        this.connectionManager = new ConnectionManager();
    }

    public static LoginInformationDao getInstance() {
        if (instance == null) {
            instance = new LoginInformationDao();
        }
        return instance;
    }

    public LoginInformation create(LoginInformation logInfo) throws SQLException {
        String insertLoginInformations = "INSERT INTO LoginInformation(loginId,userId,sessionStart,userOrAdmin) VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertLoginInformations);
            insertStmt.setInt(1, logInfo.getLoginId());
            insertStmt.setInt(2, logInfo.getPerson().getUserId());
            insertStmt.setTimestamp(3, logInfo.getSessionStart());
            insertStmt.setString(4, logInfo.getUserOrAdmin().name());
            insertStmt.executeUpdate();
            return logInfo;
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

    public LoginInformation getLoginInformationByLoginInformationId(int loginId) throws SQLException {
        String selectLogInfo = "SELECT loginId,userId,sessionStart,userOrAdmin FROM LoginInformation WHERE loginId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectLogInfo);
            selectStmt.setInt(1, loginId);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultLoginId = loginId;
                int userId = results.getInt("userId");
                Timestamp sessionStart = results.getTimestamp("sessionStart");
                LoginInformation.userOrAdmin userOrAdmin = LoginInformation.userOrAdmin.valueOf(results.getString("userOrAdmin"));
                Persons person = personsDao.getPersonByUserId(userId);
                LoginInformation logInfo = new LoginInformation(resultLoginId, person, sessionStart, userOrAdmin);
                return logInfo;
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

    public List<LoginInformation> getLoginInformationsByUserId(int userId) throws SQLException {
        List<LoginInformation> logInfoList = new ArrayList<LoginInformation>();
        String selectLoginInfo =
                "SELECT loginId,userId,sessionStart,userOrAdmin " +
                    "FROM LoginInformation " +
                    "WHERE userId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectLoginInfo);
            selectStmt.setInt(1, userId);
            results = selectStmt.executeQuery();
            while(results.next()) {
            	int loginId = results.getInt("loginId");
                Timestamp sessionStart = results.getTimestamp("sessionStart");
                LoginInformation.userOrAdmin userOrAdmin = LoginInformation.userOrAdmin.valueOf(results.getString("userOrAdmin"));
                Persons person = personsDao.getPersonByUserId(userId);
                LoginInformation logInfo = new LoginInformation(loginId, person, sessionStart, userOrAdmin);
                logInfoList.add(logInfo);
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
        return logInfoList;
    }

    
	public LoginInformation updateSessionStart(LoginInformation logInfo, Timestamp newSession) throws SQLException  {
		String updateSessionStart = "UPDATE LoginInformation SET sessionStart=? WHERE loginId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateSessionStart);
			updateStmt.setTimestamp(1, newSession);
			updateStmt.setInt(2, logInfo.getLoginId());
			updateStmt.executeUpdate();

			logInfo.setSessionStart(newSession);;
			return logInfo;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}


    public LoginInformation delete(LoginInformation loginInfo) throws SQLException {
        String deleteLogInfo = "DELETE FROM LoginInformation WHERE loginId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteLogInfo);
            deleteStmt.setInt(1, loginInfo.getLoginId());
            deleteStmt.executeUpdate();
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
