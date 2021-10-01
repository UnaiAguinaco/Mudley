package Conexiones;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
public class Conexion {
	
	public String guardarUsuario(String comando,String json,String method){
		String query_url = "http://localhost:1880/"+comando+"";		
		String result="";
	    try {
		       URL url = new URL(query_url);
		       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		       conn.setConnectTimeout(5000);
		       conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		       conn.setDoOutput(true);
		       conn.setDoInput(true);
		       conn.setRequestMethod(method);
		       OutputStream os = conn.getOutputStream();
		       if(json!=null) {
		    	   os.write(json.getBytes("UTF-8"));
		       }		       
		       os.close(); 
		       // read the response
			   
		       InputStream in = new BufferedInputStream(conn.getInputStream());
		       result = IOUtils.toString(in, "UTF-8");				
		       in.close();
		       conn.disconnect();
		       } catch (Exception e) {
					
				}
	    return result;
	}

}
