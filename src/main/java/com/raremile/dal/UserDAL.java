package com.raremile.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.raremile.common.DatabaseManager;
import com.raremile.entities.*;
import com.raremile.exceptions.NoUserException;

/**
 * Class to allow abstract access to the User table of the DB.
 * @author ghanashyam
 *
 */
public class UserDAL{
	private static final Logger LOG = Logger.getLogger(com.raremile.dal.UserDAL.class);
	private static final String SELECT_QUERY = "SELECT * FROM USER WHERE USER_NAME = ?";

	static {
		// Load the driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException ex) {
			// TODO: handle specialized exception
			LOG.error("Could not load the driver", ex);
			throw new RuntimeException("Could not load the driver.");
		}
	}

	/**
	 * Method to get a DB connection.
	 * @param dbName
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @return
	 */
	public Connection getConnection(){
		Connection con = null;
		con = DatabaseManager.getConnection();
		return con;
	}

	/**
	 * Method to close all DB objects.
	 * @param pstmt
	 * @param rs
	 * @param con
	 */
	public void closeDBObjects(PreparedStatement pstmt, ResultSet rs, Connection con){
		LOG.info("Closing DB objects.");
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
		LOG.info("Closed DB objects.");
	}

	/**
	 * Method to find a user by user name
	 * @param username
	 * @return
	 */
	public User findUser(String username){
		LOG.info("Finding user with name : " + username);
		User user = new User();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SELECT_QUERY);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.first()){
				user.setUserID(rs.getInt("USER_ID"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setUserName(rs.getString("USER_NAME"));
				LOG.info("User found.");
			}
			else{
				LOG.error("User doesn't exist.");
				throw new NoUserException("No match found for the entered user.");
			}
		}
		catch (SQLException e) {
			LOG.error("Could not load the driver", e);
			throw new RuntimeException("SQLException while inserting test data.");
		}
		finally {
			closeDBObjects(pstmt, rs, con);
		}
		return user;
	}
}