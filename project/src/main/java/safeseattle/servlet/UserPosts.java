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

@WebServlet("/userposts")
public class UserPosts extends HttpServlet {
	
	protected PostsDao postsDao;
	
	@Override
	public void init() throws ServletException {
		postsDao = PostsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        List<Posts> posts = new ArrayList<Posts>();
        
        String userId = req.getParameter("userId");
        
        try {
	        if (userId != null && !userId.trim().isEmpty()) {
	        	posts = postsDao.getPostsByUserId(Integer.parseInt(userId));
	        	messages.put("title", "Posts for UserId " + userId);
	        } else {
	        	messages.put("title", "Invalid UserId.");
	        }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        req.setAttribute("userPosts", posts);
        req.getRequestDispatcher("/UserPosts.jsp").forward(req, resp);
	}
}