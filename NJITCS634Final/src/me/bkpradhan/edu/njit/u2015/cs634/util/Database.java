/** This is an implementation of the DT Classification algorithm. 
* 
* Copyright (c) 2015 Bijaya Kumar Pradhan - bp249@njit.edu
* 
* This file is part of the CS634 - Data Mining - Final Project
* 
* 
* @author bkpradhan
*/


package me.bkpradhan.edu.njit.u2015.cs634.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Database {
	 private Connection connection = null;
	 private String dbProvider=null;
	 public static String DB_MYSQL="mysql";
	 public static String DB_ORACLE="oracle";
	 public static String DB_JAVA="java";
	 public static String DB_MEMORY="memory";

public Database(String db){
		 this.dbProvider=db;
	 }
	 
 public static void main(String[] args){
	new Database(DB_MYSQL).getConnection();
	 
 }

 public Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
             Properties prop = new Properties();
                InputStream inputStream = Database.class.getResourceAsStream("/config.properties");//new FileInputStream("config.properties");
                prop.load(inputStream);
                String driver = prop.getProperty(dbProvider+"_driver");
                String url = prop.getProperty(dbProvider+"_url");
                String user = prop.getProperty(dbProvider+"_user");
                String password = prop.getProperty(dbProvider+"_password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("DB "+connection.getCatalog()+" Connection: [ OK ]");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return connection;
        }

    }
    
  
}