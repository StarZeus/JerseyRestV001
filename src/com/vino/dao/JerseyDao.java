package com.vino.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.*;
import javax.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.vino.util.ToJASON;


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
	public static String getProductsForBrand(String brand){
		String searchResult=null;
		PreparedStatement ps=null;
		Connection con=null;
		
		JSONArray jsonArray=null;
		try{
			con=JerseyDao.getConnection().getConnection();
			ps=con.prepareStatement("select * from \"PC_PARTS\" where \"PC_PARTS_MAKER\" = '" + brand + "'");
			
			ResultSet rs=ps.executeQuery();
			
			jsonArray = new ToJASON().resultSetToJsonArray(rs);
			
			searchResult = jsonArray.toString();
			
			ps.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return "Error while getting products 101";
		}finally{
			try {
				if(con != null) con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return "Error while getting products 201";
			}
		}
		
		return searchResult;
		
	}

}
