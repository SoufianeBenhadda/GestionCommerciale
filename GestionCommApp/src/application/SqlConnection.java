package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnection {
	public static Connection getConnection(){
        Connection conn;
       
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion", "root","");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
	public static void executeQuery(String query) {
        Connection conn = SqlConnection.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
	
	
	public static void create_DB_if_Not_exists() throws SQLException
	{
		
			Connection	conn= DriverManager.getConnection("jdbc:mysql://localhost:3306?allowMultiQueries=true","root","");
			Statement req = conn.createStatement();
			String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='gestion'";
			ResultSet rs = req.executeQuery(query);                  
			rs.next();
			boolean exists = rs.getInt("COUNT(*)") > 0;
			if(!exists) 
			{
				String sql="create database gestion;" + 
						"use gestion;" + 
						"create table article( id INT(11) NOT NULL AUTO_INCREMENT, nom_article VARCHAR(256) NULL DEFAULT NULL, prix FLOAT NULL DEFAULT NULL, quantite INT(11) UNSIGNED ZEROFILL NULL DEFAULT NULL, created_at DATE NULL DEFAULT NULL, image_article BLOB NULL DEFAULT NULL, PRIMARY KEY (id) USING BTREE);" + 
						"create table message( id INT(11) NOT NULL AUTO_INCREMENT, alert VARCHAR(256) NOT NULL, PRIMARY KEY (id) USING BTREE );" +
						"create table vente( id INT(11) NOT NULL AUTO_INCREMENT, article VARCHAR(256) NULL DEFAULT NULL,client INT(11) NULL DEFAULT NULL, prixVente VARCHAR(256) NULL DEFAULT NULL, quantite INT(11) NULL DEFAULT NULL,total DOUBLE NULL DEFAULT NULL, benifice FLOAT NULL DEFAULT NULL, PRIMARY KEY (id) USING BTREE);";
			
				req.executeUpdate(sql);
				
			}
			else
			{
				String sql="use gestion;";
				req.executeUpdate(sql);
			}
				
			
			
		}
		
		
	}

