package safeseattle.dal;

import safeseattle.model.Persons;
import safeseattle.model.UserType;
import safeseattle.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDao extends PersonsDao {
    protected ConnectionManager connectionManager;

    private static UsersDao instance = null;

    protected UsersDao() {
        this.connectionManager = new ConnectionManager();
    }

    public static UsersDao getInstance() {
        if (instance == null) {
            instance = new UsersDao();
        }
        return instance;
    }

    public Users create(Users user) throws SQLException {
        String insertUser = "INSERT INTO Users(userId,userType) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertUser);
            insertStmt.setInt(1, user.getUserId());
            insertStmt.setString(2, user.getUserType().name());
            insertStmt.executeUpdate();
            return user;
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

    public Users getUserByUserId(int userId) throws SQLException {
        String selectUser = "SELECT userId, userType FROM Users WHERE userId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        Persons person = super.getPersonByUserId(userId);
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUser);
            selectStmt.setInt(1, userId);

            results = selectStmt.executeQuery();
            if(results.next()) {
                String userType = results.getString("userType");

                Users user = new Users(person, UserType.valueOf(userType));
                return user;
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

    public List<Users> getUsersByUserType(UserType userType) throws SQLException {
        List<Users> usersList = new ArrayList<Users>();
        String selectUsers =
                "SELECT userId,userType " +
                    "FROM Users " +
                    "WHERE userType=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUsers);
            selectStmt.setString(1, userType.name());
            results = selectStmt.executeQuery();
            while(results.next()) {
                int userId = results.getInt("userId");
                Persons person = super.getPersonByUserId(userId);
                Users user = new Users(person, userType);
                usersList.add(user);
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
        return usersList;
    }

    public List<Users> getUsersByNeighborhood(String neighborhood) throws SQLException {
        List<Users> usersList = new ArrayList<Users>();
        String selectUsers =
                "SELECT Users.userId AS userId, Users.userType AS userType, Persons.username AS username, " +
                "Persons.firstName AS firstName, Persons.lastName AS lastName, Persons.neighborhood as neighborhood " +
                    "FROM Users INNER JOIN Persons ON Users.userId = Persons.userId " +
                    "WHERE Persons.neighborhood=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUsers);
            selectStmt.setString(1, neighborhood);
            results = selectStmt.executeQuery();
            while(results.next()) {
                int userId = results.getInt("userId");
                String userType = results.getString("userType");
                Persons person = super.getPersonByUserId(userId);
                Users user = new Users(person, UserType.valueOf(userType));
                usersList.add(user);
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
        return usersList;
    }
    
	public Users updateUserType(Users user, UserType userType) throws SQLException  {
		String updateUserType = "UPDATE Users SET userType=? WHERE userId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUserType);
			updateStmt.setString(1, userType.name());
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();

			user.setUserType(userType);;
			return user;
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


    public Users delete(Users user) throws SQLException {
        String deleteUser = "DELETE FROM Users WHERE userId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUser);
            deleteStmt.setInt(1, user.getUserId());
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
