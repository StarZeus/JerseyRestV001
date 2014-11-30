package com.vino.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

public class ToJASON {
	JSONObject jObject=null;
	JSONArray jArray = new JSONArray();
	
	public JSONArray resultSetToJsonArray(ResultSet resultSet){
		
		try {
			ResultSetMetaData rsmd=resultSet.getMetaData();
			
			while(resultSet.next()){
				
				jObject=new JSONObject();
				for(int i=1;i<=rsmd.getColumnCount();i++){
//Encoding response for security (to avoid sending any possible script from DB - Starts					
					String temp=resultSet.getString(i);
					temp=ESAPI.encoder().canonicalize(temp);
					temp=ESAPI.encoder().encodeForHTML(temp);
//Encoding response for security (to avoid sending any possible script from DB - Ends					
					
					jObject.put(rsmd.getColumnName(i), temp);
				}
				jArray.put(jObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return jArray;
	}
}
