package com.vino.rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.vino.bean.PcPart;
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
	
	@GET
	@Path("/brandproducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts(@QueryParam("brand") String brand){
		String returnString;
		
		if(brand != null){
			returnString = JerseyDao.getProductsForBrand(brand);
			
		}else{
			return Response.status(202).entity("Brand name missing !").build();
		}
			
		return Response.ok(returnString).build();
		
	}
	
	
	//Method that does same funcationality as getProducts() as above but using PathParam
	@GET
	@Path("/brandproducts/{brand}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductsViaPath(@PathParam("brand") String brand){
		String returnString;
		
		if(brand != null){
			returnString = JerseyDao.getProductsForBrand(brand);
			
		}else{
			return Response.status(202).entity("Brand name missing !").build();
		}
			
		return Response.ok(returnString).build();
		
	}
	
	//Post method to insert data into our PC_PARTS table
	@POST
	@Path("/addPcPartV1")
	//@Consumes(MediaType.APPLICATION_JSON) - way to decline single consume type
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	public Response putPCPartProducts(String inputJasonString){

		int completionCode=200;
		
		if(inputJasonString != null){

			try {
				ObjectMapper mapper=new ObjectMapper();								// Parsing data with Jackson (JSON) parser
				PcPart part=mapper.readValue(inputJasonString, PcPart.class);
				completionCode = JerseyDao.insertPCParts(part.getPart_pk(), part.getPart_title(), part.getPart_code(), part.getPart_maker(), part.getPart_count(), part.getPart_desc());
			} catch (IOException e) {
				completionCode=500;
			}
			
		}else{
			completionCode = 500;
		}
			
		if(completionCode !=200) return Response.status(completionCode).entity("An error occured while adding product").build();
		return Response.ok("Product added successfully !").build();
		
	}
	
	
	//Clone of the above Post method to insert data into our PC_PARTS table, but using JasonObject to part the input message
	@POST
	@Path("/addPcPartV2")
	//@Consumes(MediaType.APPLICATION_JSON) - way to decline single consume type
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
	public Response putPCPartProductsDifferently(String inputJasonString){
		int completionCode=200;
		
		if(inputJasonString != null){

			try {
				JSONObject jObject=new JSONObject(inputJasonString);								// Parsing data with Jackson (JSON) parser
				completionCode = JerseyDao.insertPCParts(jObject.optInt("PC_PART_PK"), 
						                                 jObject.optString("PC_PART_TITLE"),
						                                 jObject.optInt("PC_PART_CODE"),
						                                 jObject.optString("PC_PART_MAKER"),
						                                 jObject.optInt("PC_PART_AVAIL"),
						                                 jObject.optString("PC_PART_DESC"));
			} catch (Exception e) {
				completionCode=500;
			}
			
		}else{
			completionCode = 500;
		}
			
		if(completionCode !=200) return Response.status(completionCode).entity("An error occured while adding product").build();
		return Response.ok("Product added successfully !").build();
		
	}
}
