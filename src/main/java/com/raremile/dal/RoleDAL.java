package com.raremile.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raremile.common.DatabaseManager;
import com.raremile.exceptions.NoRoleException;
import com.raremile.entities.*;

/**
 * Class to allow abstract access to the UserRole table of the DB.
 * @author ghanashyam
 *
 */
public class RoleDAL {

	private static final Logger LOG = Logger.getLogger(com.raremile.dal.UserDAL.class);
	private static final String SELECT_QUERY = "SELECT ROLE FROM USER_PROFILE WHERE USER_ID = ?";

	static {
		// Load the driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException ex) {
			LOG.error("Could not load the driver", ex);
			throw new RuntimeException("Could not load the driver.");
		}
	}

	/**
	 * Method to get a DB connection from the Database Manager.
	 * @return
	 */
	public Connection getConnection(){
		Connection con = null;
		con = DatabaseManager.getConnection();
		return con;
	}

	/**
	 * Method to close DB objects
	 * @param pstmt
	 * @param rs
	 * @param con
	 */
	public void closeDBObjects(PreparedStatement pstmt, ResultSet rs, Connection con){
		LOG.info("Closing DB Objects");
		if(pstmt != null){
			try {
				pstmt.close();
			}
			catch (SQLException e) {
				LOG.error("Closing the prepared statement failed.");
				e.printStackTrace();
			}
		}
		if (rs != null){
			try {
				rs.close();
			}
			catch (SQLException e) {
				LOG.error("Closing the result set failed.");
				e.printStackTrace();
			}
		}
		if(con != null){
			try {
				con.close();
			}
			catch (SQLException e) {
				LOG.error("Closing the connection failed.");
				e.printStackTrace();
			}
		}
		LOG.info("DB objects closed successfully.");
	}

	/**
	 * Method to find and return a list of roles of a user
	 * @param id
	 * @return
	 */
	public List<String> findRoles(int id){
		LOG.info("Finding all the roles where user ID : " + id);
		List<String> roles = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SELECT_QUERY);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.first()){
				roles.add(rs.getString("ROLE"));
				while (rs.next()) {
					roles.add(rs.getString("ROLE"));
				}
				LOG.info("Roles retrieved.");
			}
			else{
				LOG.error("No role found.");
				throw new NoRoleException("The user has no role.");
			}
		}
		catch (SQLException e) {
			LOG.error("Could not load the driver", e);
			throw new RuntimeException("SQLException");
		}
		finally {
			closeDBObjects(pstmt, rs, con);
		}
		return roles;
	}
}

