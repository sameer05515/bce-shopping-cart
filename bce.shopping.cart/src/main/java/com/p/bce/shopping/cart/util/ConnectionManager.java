/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.p.bce.shopping.cart.util;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * @author PREMENDRA
 */
public class ConnectionManager {

	private static ConnectionManager thisInstance;

	private ComboPooledDataSource cpdsInterviewMgmtDB;
	
	static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

	// static {
	// try {
	// PropertyUtil objPropertyUtil = PropertyUtil.getInstance();
	//
	// // Class.forName("com.mysql.jdbc.Driver");
	// // con = DriverManager.getConnection(
	// // "jdbc:mysql://localhost:3306/interview_ques", "root",
	// // "vinu123");
	//
	// Class.forName(objPropertyUtil.getValue(PropertyFile.DB_PROPRTY,
	// "db_driver"));
	// con = DriverManager
	// .getConnection(objPropertyUtil.getValue(
	// PropertyFile.DB_PROPRTY, "db_url"), objPropertyUtil
	// .getValue(PropertyFile.DB_PROPRTY, "db_user_name",
	// false), objPropertyUtil.getValue(
	// PropertyFile.DB_PROPRTY, "db_password", false));
	// } catch (ClassNotFoundException ex) {
	// ex.printStackTrace();
	// // Logger.getLogger(StudentNameFeeder.class.getName()).log(Level.SEVERE,
	// // null, ex);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }

	private ConnectionManager() {
		makeComboPooledDataSourceObject();
	}

	private void makeComboPooledDataSourceObject() {

		PropertyUtil objPropertyUtil = PropertyUtil.getInstance();

		/** _______________c3p0 connection object for iagent database ____ */
		String iagentDriverClassName = objPropertyUtil.getValue(PropertyFile.DB_PROPRTY, "db_driver");
		String iagentDBURL = objPropertyUtil.getValue(PropertyFile.DB_PROPRTY, "db_url");
		String iagentDBUserName = objPropertyUtil.getValue(PropertyFile.DB_PROPRTY, "db_user_name", false);
		String iagentDBPassword = objPropertyUtil.getValue(PropertyFile.DB_PROPRTY, "db_password", false);
		try {

			cpdsInterviewMgmtDB = new ComboPooledDataSource();
			cpdsInterviewMgmtDB.setDriverClass(iagentDriverClassName);
			cpdsInterviewMgmtDB.setJdbcUrl(iagentDBURL);
			cpdsInterviewMgmtDB.setUser(iagentDBUserName);
			cpdsInterviewMgmtDB.setPassword(iagentDBPassword);
		} catch (Exception e) {
			System.out.println("ERROR WHILE CONNECTION TO IAGENT DATA SOURCE : " + e);
			logger.info("ERROR WHILE CONNECTION TO IAGENT DATA SOURCE : ",e);
		}

	}

	public static ConnectionManager getInstance() {
		if (thisInstance == null) {
			thisInstance = new ConnectionManager();
		}
		return thisInstance;
	}

	public Connection getConnection() {
		Connection con = null;
		con = getInterviewMgmtDBConnection();
		return con;
	}

	private Connection getInterviewMgmtDBConnection() {
		try {
			Connection iagentDataCon = cpdsInterviewMgmtDB.getConnection();

			return iagentDataCon;
		} catch (Throwable e) {
			System.out.println("ERROR WHILE CONNECTION TO IAGENT DATA SOURCE : " + e);
			logger.info("ERROR WHILE CONNECTION TO IAGENT DATA SOURCE : " ,e);
			return null;
		}
	}
}
