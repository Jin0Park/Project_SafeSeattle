package safeseattle.dal;

import safeseattle.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminsDao extends PersonsDao{
    protected ConnectionManager connectionManager;

    private static AdminsDao instance = null;

    protected AdminsDao() {
        this.connectionManager = new ConnectionManager();
    }

    public static AdminsDao getInstance() {
        if (instance == null) {
            instance = new AdminsDao();
        }
        return instance;
    }

    public Admins create(Admins admin) throws SQLException {
        String insertAdmin = "INSERT INTO Admins (userId,editAccess) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertAdmin);
            insertStmt.setInt(1, admin.getUserId());
            insertStmt.setString(2, admin.getEditAccess().name());

            insertStmt.executeUpdate();
            return admin;
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

    public Admins getAdminByUserId(int userId) throws SQLException {
        String selectAdmin = "SELECT userId, editAccess FROM Admins WHERE userId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        Persons person = super.getPersonByUserId(userId);
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAdmin);
            selectStmt.setInt(1, userId);

            results = selectStmt.executeQuery();
            if(results.next()) {
                String editAccess = results.getString("editAccess");

                Admins admin = new Admins(person, EditAccess.valueOf(editAccess));
                return admin;
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

    public List<Admins> getAdminsByEditAccess(EditAccess editAccess) throws SQLException {
        List<Admins> adminsList = new ArrayList<Admins>();
        String selectAdmins =
                "SELECT userId,editAccess " +
                        "FROM Admins " +
                        "WHERE editAccess=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAdmins);
            selectStmt.setString(1, editAccess.name());
            results = selectStmt.executeQuery();
            while(results.next()) {
                int userId = results.getInt("userId");
                Persons person = super.getPersonByUserId(userId);
                Admins admin = new Admins(person, editAccess);
                adminsList.add(admin);
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
        return adminsList;
    }
    
	public Admins updateEditAccess (Admins admin, EditAccess editAccess) throws SQLException  {
		String updateEditAccess = "UPDATE Admins SET editAccess=? WHERE userId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateEditAccess);
			updateStmt.setString(1, editAccess.name());
			updateStmt.setInt(2, admin.getUserId());
			updateStmt.executeUpdate();

			admin.setEditAccess(editAccess);;
			return admin;
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

    public Admins delete(Admins admin) throws SQLException {
        String deleteAdmin = "DELETE FROM Admins WHERE userId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteAdmin);
            deleteStmt.setInt(1, admin.getUserId());
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
