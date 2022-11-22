package safeseattle.dal;

import safeseattle.model.Persons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonsDao {
    protected ConnectionManager connectionManager;

    private static PersonsDao instance = null;

    protected PersonsDao() {
        this.connectionManager = new ConnectionManager();
    }

    public static PersonsDao getInstance() {
        if (instance == null) {
            instance = new PersonsDao();
        }
        return instance;
    }

    public Persons create(Persons person) throws SQLException {
        String insertPerson = "INSERT INTO Persons(username,password,firstName,lastName,phoneNumber,email,address,neighborhood) VALUES(?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertPerson, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, person.getUsername());
            insertStmt.setString(2, person.getPassword());
            insertStmt.setString(3, person.getFirstName());
            insertStmt.setString(4, person.getLastName());
            insertStmt.setString(5, person.getPhoneNumber());
            insertStmt.setString(6, person.getEmail());
            insertStmt.setString(7, person.getAddress());
            insertStmt.setString(8, person.getNeighborhood());

            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int userId = -1;
            if(resultKey.next()) {
                userId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            person.setUserId(userId);
            return person;
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

    public Persons getPersonByUserId(int userId) throws SQLException {
        String selectUser = "SELECT userId, username,password,firstName,lastName,phoneNumber,email,address,neighborhood FROM Persons WHERE userId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUser);
            selectStmt.setInt(1, userId);

            results = selectStmt.executeQuery();
            if(results.next()) {
                String username = results.getString("username");
                String password = results.getString("password");
                String firstName = results.getString("firstName");
                String lastName = results.getString("lastName");
                String phoneNumber = results.getString("phoneNumber");
                String email = results.getString("email");
                String address = results.getString("address");
                String neighborhood = results.getString("neighborhood");

                Persons persons = new Persons(userId, username, password, firstName, lastName, phoneNumber, email, address, neighborhood);
                return persons;
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
    
	public Persons updateNeighborhood(Persons person, String neighborhood) throws SQLException  {
		String updateNeighborhood = "UPDATE Persons SET neighborhood=? WHERE userId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateNeighborhood);
			updateStmt.setString(1, neighborhood);
			updateStmt.setInt(2, person.getUserId());
			updateStmt.executeUpdate();

			person.setNeighborhood(neighborhood);
			return person;
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

    public Persons delete(Persons person) throws SQLException {
        String deletePerson = "DELETE FROM Persons WHERE userId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletePerson);
            deleteStmt.setInt(1, person.getUserId());
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
}
