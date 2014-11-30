package com.vino.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

import com.vino.dao.JerseyDao;
import com.vino.util.ToJASON;

@Path("/inventory")
public class Inventory {
	@GET
	@Path("/stockreport")
	@Produces(MediaType.APPLICATION_JSON)
	public String getInventory(){
		PreparedStatement ps=null;
		String returnString = null;
		Connection con=null;
		
		JSONArray jsonArray=null;
		try{
			con=JerseyDao.getConnection().getConnection();
			ps=con.prepareStatement("select * from \"PC_PARTS\"");
			
			ResultSet rs=ps.executeQuery();
			
			jsonArray = new ToJASON().resultSetToJsonArray(rs);
			
			returnString = jsonArray.toString();
			
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
		
		return returnString;
	}
}
