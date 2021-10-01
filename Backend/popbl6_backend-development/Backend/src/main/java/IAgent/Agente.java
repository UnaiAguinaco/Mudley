package IAgent;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import net.javaguides.springboot.model.Busqueda;
import net.javaguides.springboot.model.Resultados;
import net.javaguides.springboot.model.User;

public class Agente{
	Busqueda busqueda;
	User user;
	Resultados resultado;
	int accion;
	
	public int getAccion() {
		return accion;
	}
	public void setAccion(int accion) {
		this.accion = accion;
	}
	public Agente(Busqueda busqueda, User user) {
		this.busqueda = busqueda;
		this.user = user;
		resultado=null;
	}

	public void obtenerRecomendaciones(Busqueda busqueda, User user) {
		String query_url="";
		int budget;
		String json="";
		if(busqueda!=null) {
			budget=Integer.parseInt(busqueda.getMin())+100;
		    json = "{ \"name\" :\""+user.getName()+"\" ,\"city\" : \""+busqueda.getCity()+"\",\"date\" : \""+busqueda.getFecha()+"\",\"gender\" : \""+busqueda.getGenderName()+"\",\"budget\" : \""+budget+"\"}";  
		    query_url = "http://localhost:1880/mostrar";
		}else {
		    json = "{ \"name\" :\""+user.getName()+"\"}"; 
		    if(accion==0) {
		    	query_url = "http://localhost:1880/mostrarRecomendaciones";
		    }else if(accion==1) { 
		    	query_url = "http://localhost:1880/mostrarNuevos";
		    }else if(accion==2) {
		    	query_url = "http://localhost:1880/mostrarVistos";
		    }else {
		    	query_url = "http://localhost:1880/mostrarNuestros";
		    }

		}
	    Resultados resultados=null;
	    URL url;
	    try {
	       url = new URL(query_url);	 
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();       
	       conn.setConnectTimeout(5000);
	       conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	       conn.setDoOutput(true);
	       conn.setDoInput(true);
	       conn.setRequestMethod("POST");
	       OutputStream os = conn.getOutputStream();
	       if(json!=null) {
		       os.write(json.getBytes("UTF-8"));
	       }
	       os.close(); 
	       // read the response	   
	       InputStream in = new BufferedInputStream(conn.getInputStream());
	       String result = IOUtils.toString(in, "UTF-8");
		   Gson gson = new Gson();
		   
		   resultado = gson.fromJson(result, Resultados.class);
	       in.close();
	       conn.disconnect();
	       } catch (Exception e) {
				System.out.println(e);
	       }	    
	}
	public Resultados getResultado() {
		return resultado;
	}
	public void setResultado(Resultados resultado) {
		this.resultado = resultado;
	}
}

