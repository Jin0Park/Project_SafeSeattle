package safeseattle.dal;

import safeseattle.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class PostsDao {
	protected ConnectionManager connectionManager;

	private static PostsDao instance = null;
	protected PostsDao() {
		connectionManager = new ConnectionManager();
	}
	public static PostsDao getInstance() {
		if(instance == null) {
			instance = new PostsDao();
		}
		return instance;
	}
	
	/**
	 * Create the Comments instance.
	 * This runs a CREATE statement.
	 */
	public Posts create(Posts post) throws SQLException {
		String insertPost =
			"INSERT INTO Posts(PostId,UserId,ReportId,Title,Content,Created) " +
			"VALUES(?,?,?,?,?,?);"; 
		Connection connection = null; 
		PreparedStatement insertStmt = null; 
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPost);
			insertStmt.setInt(1, post.getPostId());
			insertStmt.setInt(2, post.getUser().getUserId());
			insertStmt.setString(3, post.getReport().getReportId());
			insertStmt.setString(4, post.getTitle());
			insertStmt.setString(5, post.getContent());
			insertStmt.setTimestamp(6, new Timestamp(post.getCreated().getTime()));
			insertStmt.executeUpdate();
			return post;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	/**
	 * Get the Posts record by fetching it from MySQL instance.
	 * This runs a SELECT/READ statement and returns a single Posts instance.
	 */
	public Posts getPostById(int postId) throws SQLException {
		String selectPost =
			"SELECT PostId,UserId,ReportId,Title,Content,Created " +
			"FROM Posts " +
			"WHERE PostId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPost);
			selectStmt.setInt(1, postId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ReportsDao reportsDao = ReportsDao.getinstance();
			if(results.next()) {
				int resultPostId = results.getInt("PostId");
				int userId = results.getInt("UserId");
				String reportId = results.getString("ReportId");
				String title = results.getString("Title");
				String content = results.getString("Content");
				Timestamp created =  new Timestamp(results.getTimestamp("Created").getTime());
				
				
				Users user = usersDao.getUserByUserId(userId); // implement in users dao?
				Reports report = reportsDao.getReportById(reportId); // implement in reports dao?
				Posts post = new Posts(resultPostId, user, report, title,
					content, created);
				return post;
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
	 * Get the Posts record by fetching it from MySQL instance.
	 * This runs a SELECT/READ statement and returns a single Posts instance.
	 */
	public List<Posts> getPostsByUserId(int userId) throws SQLException {
		String selectPost =
			"SELECT PostId,UserId,ReportId,Title,Content,Created " +
			"FROM Posts " +
			"WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Posts> posts = new ArrayList<>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPost);
			selectStmt.setInt(1, userId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ReportsDao reportsDao = ReportsDao.getinstance();
			while(results.next()) {
				int postId = results.getInt("PostId");
				int resultUserId = results.getInt("UserId");
				String reportId = results.getString("ReportId");
				String title = results.getString("Title");
				String content = results.getString("Content");
				Timestamp created =  new Timestamp(results.getTimestamp("Created").getTime());
				
				Users user = usersDao.getUserByUserId(resultUserId); // implement in users dao?
				Reports report = reportsDao.getReportById(reportId); // implement in reports dao?
				Posts post = new Posts(postId, user, report, title,
					content, created);
				posts.add(post);
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
		return posts;
	}
	
	/**
	 * Update the content of the Posts instance.
	 * This runs a UPDATE statement.
	 */
	public Posts updateContent(Posts post, String newContent) throws SQLException {
		String updatePost = "UPDATE Posts SET Content=?,Created=? WHERE PostId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePost);
			updateStmt.setString(1, newContent);
			// Sets the Created timestamp to the current time.
			Timestamp newCreatedTimestamp = new Timestamp(System.currentTimeMillis());
			// updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setTimestamp(2, newCreatedTimestamp);
			updateStmt.setInt(3, post.getPostId());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			post.setContent(newContent);
			post.setCreated((Timestamp) newCreatedTimestamp);
			return post;
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
	
	/**
	 * Delete the Posts instance.
	 * This runs a DELETE statement.
	 */
	public Posts delete(Posts post) throws SQLException {
		String deletePost = "DELETE FROM Posts WHERE PostId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePost);
			deleteStmt.setInt(1, post.getPostId());
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