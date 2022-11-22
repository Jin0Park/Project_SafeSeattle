package safeseattle.servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import safeseattle.dal.CommentsDao;
import safeseattle.model.Comments;


@WebServlet("/deletecomments")
public class DeleteComments extends HttpServlet {
	protected CommentsDao commentsDao;

	@Override
	public void init() throws ServletException {
		commentsDao = CommentsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Comment");        
        req.getRequestDispatcher("/DeleteComments.jsp").forward(req, resp);
	}

	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String commentId = req.getParameter("commentId");
        if (commentId == null || commentId.trim().isEmpty()) {
            messages.put("title", "Invalid commentId");
            messages.put("disableSubmit", "true");
        } else {
        	
	        Comments deleteComment = new Comments(Integer.parseInt(commentId));
	        try {
	        	deleteComment = commentsDao.delete(deleteComment);
	        	// Update the message.
		        if (deleteComment== null) {
		            messages.put("title", "Successfully deleted CommentId " +commentId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete CommentId " + commentId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/DeleteComments.jsp").forward(req, resp);
    }
}

