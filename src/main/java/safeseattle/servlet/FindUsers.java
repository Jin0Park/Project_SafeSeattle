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

@WebServlet("/findusers")
public class FindUsers extends HttpServlet {
	protected UsersDao usersDao;
	
	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Users> users = new ArrayList<Users>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String neighborhood = req.getParameter("neighborhood");
        if (neighborhood == null || neighborhood.trim().isEmpty()) {
            messages.put("success", "Please enter a valid neighborhood.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		users = usersDao.getUsersByNeighborhood(neighborhood);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + neighborhood);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousNeighborhood", neighborhood);
        }
        req.setAttribute("users", users);
        
        req.getRequestDispatcher("/FindUsers.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Users> users = new ArrayList<Users>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindUsers.jsp).
        String neighborhood = req.getParameter("neighborhood");
        if (neighborhood == null || neighborhood.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		users = usersDao.getUsersByNeighborhood(neighborhood);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + neighborhood);
        }
        req.setAttribute("users", users);
        
        req.getRequestDispatcher("/FindUsers.jsp").forward(req, resp);
    }
}