package com.raremile.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raremile.entities.User;
import com.raremile.entities.UserRole;
import com.raremile.dal.RoleDAL;
import com.raremile.dal.UserDAL;
import com.raremile.exceptions.NoRoleException;
import com.raremile.exceptions.NoUserException;

/**
 * Controller servlet
 * @author ghanashyam
 *
 */
public class AuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		RoleDAL roleDal = new RoleDAL();
		UserDAL userDal = new UserDAL();
		User user = new User();
		List<String> userRole = null;
		String desig = "";
		int admin = 0, dev = 0, manager = 0;
		req.setAttribute("userError", "");
		Map<String, Integer> roleMap = new HashMap<String, Integer>(){{
			put("developer", 1);
			put("manager", 2);
			put("admin", 3);
		}};
		try{
			user = userDal.findUser(username);
			if(password.equals(user.getPassword())){
				req.setAttribute("username", username);
				try{
					userRole = roleDal.findRoles(user.getUserID());
					int maxRole = 0;
					for (String role : userRole) {
						if(maxRole < roleMap.get(role)){
							maxRole = roleMap.get(role);
							desig = role;
						}
					}
					req.setAttribute("desig", desig);
					req.setAttribute("roles", userRole);
				}catch(NoRoleException e){
					req.setAttribute("roleError", "No Role found");
				}
			}else
				req.setAttribute("passwordError", "Invalid password");
		}catch(NoUserException e){
			req.setAttribute("userError", "Invalid username");
		}
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/view/auth.jsp");
		rd.forward(req, resp);
	}
}
