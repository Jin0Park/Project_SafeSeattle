package safeseattle.dal;

import safeseattle.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class NotificationsDao {
    protected ConnectionManager connectionManager;
	protected UsersDao usersDao = UsersDao.getInstance();
	protected ReportsDao reportsDao = ReportsDao.getinstance();
    private static NotificationsDao instance = null;

    protected NotificationsDao() {
        this.connectionManager = new ConnectionManager();
    }

    public static NotificationsDao getInstance() {
        if (instance == null) {
            instance = new NotificationsDao();
        }
        return instance;
    }

    public Notifications create(Notifications notification) throws SQLException {
        String insertNotification = "INSERT INTO Notifications(userId,message,notificationTime,reportId) VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertNotification, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, notification.getUser().getUserId());
            insertStmt.setString(2, notification.getMessage());
            insertStmt.setTimestamp(3, notification.getNotificationTime());
            insertStmt.setString(4, notification.getReport().getReportId());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int notificationId = -1;
            if(resultKey.next()) {
            	notificationId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            notification.setNotificationId(notificationId);
            return notification;
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

    public Notifications getNotificationByNotificationId(int notificationId) throws SQLException {
        String selectUser = "SELECT notificationId,userId,message,notificationTime,reportId FROM Notifications WHERE notificationId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUser);
            selectStmt.setInt(1, notificationId);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultNotificationId = notificationId;
            	int userId = results.getInt("userId");
                String message = results.getString("message");
                Timestamp notificationTime = results.getTimestamp("notificationTime");
                String reportId = results.getString("reportId");
                Users user = usersDao.getUserByUserId(userId);
                Reports report = reportsDao.getReportById(reportId);

                Notifications notification = new Notifications(resultNotificationId, user, message, notificationTime, report);
                return notification;
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
    
	public Notifications updateMessage(Notifications notification, String message) throws SQLException  {
		String updateMessage= "UPDATE Notifications SET message=? WHERE notificationId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateMessage);
			updateStmt.setString(1, message);
			updateStmt.setInt(2, notification.getNotificationId());
			updateStmt.executeUpdate();
			notification.setMessage(message);
			return notification;
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

    public Notifications delete(Notifications notification) throws SQLException {
        String deletePerson = "DELETE FROM Notifications WHERE notificationId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletePerson);
            deleteStmt.setInt(1, notification.getNotificationId());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Users instance.
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
    
	public List<Notifications> getNotificationByReportId(String reportId) throws SQLException {
		List<Notifications> notiList = new ArrayList<Notifications>();
		String selectRes =
			"SELECT Notifications.notificationId AS notificationId,userId,message,notificationTime,reportId " +
			"FROM Notifications " + 
			"WHERE Notifications.reportId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRes);
			selectStmt.setString(1, reportId);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int notificationId = results.getInt("notificationId");
				int userId = results.getInt("userId");
				String message = results.getString("message");
				Timestamp notificationTime = results.getTimestamp("notificationTime");
                Users user = usersDao.getUserByUserId(userId);
                Reports report = reportsDao.getReportById(reportId);
				Notifications notification = new Notifications(notificationId, user, message, notificationTime, report);				
				notiList.add(notification);
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
		return notiList;
	}
}
