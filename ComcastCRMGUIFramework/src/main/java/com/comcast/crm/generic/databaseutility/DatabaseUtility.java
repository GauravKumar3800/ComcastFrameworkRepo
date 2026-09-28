package com.comcast.crm.generic.databaseutility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class DatabaseUtility {
	Connection con;
	public void getDbconnection(String url,String username,String password) throws SQLException
	{
		try {
			Driver driver=new Driver();
			DriverManager.registerDriver(driver);

			Connection con=DriverManager.getConnection(url, username, password);
		}catch(Exception e)
		{
		}
	}
	public void getDbconnection() throws SQLException
	{
		try {
			Driver driver=new Driver();
			DriverManager.registerDriver(driver);

			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3036/projects", "root", "root");
		}catch(Exception e)
		{
		}
	}
	public void closeDbconnection() throws SQLException
	{
		con.close();
	}

	public ResultSet executeSelectQuery(String query) throws SQLException 
	{  ResultSet result = null;
	try {
		Statement stat=con.createStatement();
		result=stat.executeQuery(query);
	}catch(Exception e) {

	}

	return result;
	}	

	public int executeNonselectQuery(String query)
	{
		int result=0;
		try {
			Statement stat=con.createStatement();
			result=	stat.executeUpdate(query);
		}catch(Exception e) {
		}
		return result;
	}
}