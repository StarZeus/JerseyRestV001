package com.vino.rest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
}
