package safeseattle.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import safeseattle.model.Comments;
import safeseattle.model.Posts;
import safeseattle.model.Users;


public class CommentsDao{
	protected ConnectionManager connectionManager;
	
	private static CommentsDao instance = null;
	protected CommentsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CommentsDao getInstance() {
		if(instance == null) {
			instance = new CommentsDao();
		}
		return instance;
	}
	
	/**
	 * Create the Comments instance.
	 * This runs a CREATE statement.
	 */
	public Comments create(Comments comment) throws SQLException {
		// creating with only 4 fields because commentId is auto-incremented
		String insertComment =
			"INSERT INTO Comments(UserId,PostId,Content,Created) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertComment,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, comment.getUser().getUserId());
			insertStmt.setInt(2, comment.getPost().getPostId());
			insertStmt.setString(3, comment.getContent());
			insertStmt.setTimestamp(4, new Timestamp(comment.getCreated().getTime()));
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int commentId = -1;
			if(resultKey.next()) {
				commentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			comment.setCommentId(commentId);
			return comment;
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
	 * Get the Comments record by fetching it from your MySQL instance.
	 * This runs a SELECT/READ statement and returns a single Comments instance.
	 */
	public Comments getCommentById(int commentId) throws SQLException {
		String selectComment =
			"SELECT CommentId,UserId,PostId,Content,Created " +
			"FROM Comments " +
			"WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setInt(1, commentId);
			results = selectStmt.executeQuery();
			PostsDao postsDao = PostsDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultCommentId = results.getInt("CommentId");
				int userId = results.getInt("UserId");
				int postId = results.getInt("PostId");
				String content = results.getString("Content");
				// Date created =  new Date(results.getTimestamp("Created").getTime());
				Timestamp created = results.getTimestamp("Created");
				
				Posts post = postsDao.getPostById(postId);
				Users user = usersDao.getUserByUserId(userId); 
				Comments comment = new Comments(resultCommentId, user,
					post, content, created);
				return comment;
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
	
	public List<Comments> getCommentsByPostId(int postId) throws SQLException {
		String selectComment =
			"SELECT CommentId,UserId,PostId,Content,Created " +
			"FROM Comments " +
			"WHERE PostId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Comments> comments = new ArrayList<>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setInt(1, postId);
			results = selectStmt.executeQuery();
			PostsDao postsDao = PostsDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int commentId = results.getInt("CommentId");
				int userId = results.getInt("UserId");
				int resultPostId = results.getInt("PostId");
				String content = results.getString("Content");
				// Date created =  new Date(results.getTimestamp("Created").getTime());
				Timestamp created = results.getTimestamp("Created");
				
				Posts post = postsDao.getPostById(resultPostId);
				Users user = usersDao.getUserByUserId(userId); 
				Comments comment = new Comments(commentId, user,
						post, content, created);
				comments.add(comment);
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
		return comments;
	}
	
	/**
	 * Update the content of the Comments instance.
	 * This runs a UPDATE statement.
	 */
	public Comments updateContent(Comments comment, String newContent) throws SQLException {
		String updateComment = "UPDATE Comments SET Content=?,Created=? WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateComment);
			updateStmt.setString(1, newContent);
			Date date = new Date(System.currentTimeMillis()); // Added System.currentTimeMillis()
			Timestamp newCreatedTimestamp = new Timestamp(date.getTime());
			updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setInt(3, comment.getCommentId());
			updateStmt.executeUpdate();
			comment.setContent(newContent);
			comment.setCreated(newCreatedTimestamp);
			return comment;
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
	 * Delete the Comments instance.
	 * This runs a DELETE statement.
	 */
	public Comments delete(Comments comment) throws SQLException {
		String deleteComment = "DELETE FROM Comments WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteComment);
			deleteStmt.setInt(1, comment.getCommentId());
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

