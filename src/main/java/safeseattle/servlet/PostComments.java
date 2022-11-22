package safeseattle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import safeseattle.dal.*;
import safeseattle.model.*;

@WebServlet("/postcomments")
public class PostComments extends HttpServlet {
	
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
		
        List<Comments> comments = new ArrayList<Comments>();
        
        String postId = req.getParameter("postId");
        
        try {
	        if (postId != null && !postId.trim().isEmpty()) {
	        	comments = commentsDao.getCommentsByPostId(Integer.parseInt(postId));
	        	messages.put("title", "Comments for PostId " + postId);
	        } else {
	        	messages.put("title", "Invalid PostId.");
	        }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        req.setAttribute("postComments", comments);
        req.getRequestDispatcher("/PostComments.jsp").forward(req, resp);
	}
}