package com.wikidata.extractor.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlCon {
//	public static void main(String args[]){  
//		try{  
//			Class.forName("com.mysql.cj.jdbc.Driver");  
//			Connection con=DriverManager.getConnection(  
//			"jdbc:mysql://localhost:3306/imrelax?characterEncoding=UTF-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","admin");  
//			Statement stmt=con.createStatement();  
//			ResultSet rs=stmt.executeQuery("select * from tb_wikidata_train_docinfo");  
//			while(rs.next())  
//			System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
//			con.close();  
//		}catch(Exception e){
//			System.out.println(e);
//		}  
//	}  
	
	private static String url;
	private static String username;
	private static String password;
	
	private MysqlCon(){}
	static{
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			url = "jdbc:mysql://localhost:3306/imrelax?useUnicode=true&characterEncoding=UTF-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&connectTimeout=720000&socketTimeout=720000";
			username="root";
			password="admin";
		}catch(Exception ex){
			
		}
	}
	
	public static Connection getConnection() throws Exception{
		Connection con = DriverManager.getConnection(url, username, password);  
		return con; 
	}
	
	public static void close(Connection con,Statement stat){
		if(stat!=null){
			try{
				stat.close();
			}catch(SQLException ex){}
		}
		if(con!=null){
			try{
				con.close();
			}catch(SQLException ex){}
		}
	}
	
	public static void close(Connection con,Statement stat,ResultSet rs){
		if(rs!=null){
			try{
				rs.close();
			}catch(SQLException ex){}
		}
		if(stat!=null){
			try{
				stat.close();
			}catch(SQLException ex){}
		}
		if(con!=null){
			try{
				con.close();
			}catch(SQLException ex){}
		}
	}
	
}
