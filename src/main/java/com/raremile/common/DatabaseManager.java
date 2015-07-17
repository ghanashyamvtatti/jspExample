package com.raremile.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;


/**
 * class used to handle database connections and closing those connections.
 * @author ghanashyam
 *
 */

public class DatabaseManager{
	static Connection con = null;
	private static final Logger LOG = Logger.getLogger(com.raremile.common.DatabaseManager.class);

	/**
	 * static block to load the jdbc driver 
	 */
	static {
		// Load the driver
		LOG.info("trying to load the the jdbc driver");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			// TODO: handle specialized exception
			LOG.error("Could not load the driver", ex);
			throw new RuntimeException("Could not load the driver.");
		}
	}

	/**
	 * method to create a DB connection 
	 * @return Connection 
	 */
	public static Connection getConnection() {
		try {
			LOG.debug("calling get connection of Driver Manager");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jspdb?user=root&password=root");
		} catch (SQLException e) {
			// TODO: handle specialized exception
			LOG.error("Could not load the driver", e);
			throw new RuntimeException("SQLException while connecting to the database.");
		}
		LOG.info("Connection successfully created");
		return con;
	}

	/**
	 * method to close the existing database connections as well as Prepared Statement and ResultSet will be closed. 
	 * @param pstmt
	 * @param rs
	 * @param con
	 */

	public static void closeDBObjects(PreparedStatement pstmt, ResultSet rs, Connection con) {
		LOG.info("tring to close connection to the db, prepared stmt and result set");
		if (pstmt != null) {
			try {
				LOG.debug("closing prepared statement " + pstmt.toString());
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOG.error("pstmt closing error"+e);
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				LOG.debug("closing result set" + rs.toString());
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOG.error("result set closing error"+e);
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				LOG.debug("closing database connection" + con.toString());
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOG.error("error while closing the connection object of DB"+e);
				e.printStackTrace();
			}
		}
	}

}
