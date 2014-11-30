package com.vino.rest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.sql.*;

import com.vino.dao.*;

@Path("myjersey/v001")
public class MyJerseyRest {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getVersion(){
		return "<p>Rest Vision V001</p>";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/status")
	public String getStatus(){
		return "<p>I am well and alive</p>";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("database")
	public String returtDatabaseConnectionStatus(){
		PreparedStatement ps=null;
		String returnString = null;
		Connection con=null;
		try{
			con=JerseyDao.getConnection().getConnection();
			ps=con.prepareStatement("select to_char(current_timestamp, \'YYYY-MM-DD HH24:MI\');");
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()){
				returnString = rs.getString(1);
			}
			
			ps.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(con != null) con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "Current Time at Server : " + returnString ;
	}
}
