package com.raremile.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author ghanashyam
 *
 */
public class PropertiesManager {
	private static Properties PROP;
	static BufferedReader br = null;
	static final Logger LOG = Logger.getLogger(com.raremile.common.PropertiesManager.class);

	/**
	 * static block to create a stream and load the properties object with DB
	 * connection properties.
	 */
	static {
		LOG.info(" intitializing property object and loading it");
		PROP = new Properties();
		InputStream in;
		/*
		 * Class clazz = PropertiesManager.class.getClass(); ClassLoader
		 * classLoader= clazz.getClassLoader(); in =
		 * classLoader.getResourceAsStream("resources/dbCon.properties");
		 */
		in = PropertiesManager.class.getClassLoader().getResourceAsStream("dbCon.properties");
		try {
			LOG.info("inside the first try");
			PROP.load(in);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error("Loading of property objet failed.");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					LOG.info("closing DB connection.");
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOG.error("closing DB connection failed.");
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Returns the property value coorresponding to the key specifieid
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		LOG.info("trying to get property value for: " + key);

		String property = PROP.getProperty(key);
		// LOG.info(property);
		return property;
	}

	/**
	 * main method used for testing purposes. Un-comment it to use it.
	 * 
	 * @param args
	 */
	/*public static void main(String[] args) {
		PropertiesManager.getProperty("host");

	}*/
}
