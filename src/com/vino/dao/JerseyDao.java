package com.vino.dao;

import javax.naming.*;
import javax.sql.*;


public class JerseyDao {
	
	private static DataSource restDS=null;
	private static Context context=null;
	
	public static DataSource getConnection() throws Exception{
		
		if(restDS != null){
			return restDS;
		}
		
		try{
			if(context == null){
				context=new InitialContext();
			}
			
			restDS = (DataSource) context.lookup("java:/comp/env/jdbc/postgres");
			
			if (restDS == null){
				System.out.println("Couldn't find database!!!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return restDS;
	}

}
