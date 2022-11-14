package com.fernandopaniagua.omdbhttpclient;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteHTTP {
	public static String getStringFromURL(String strURL) throws Exception  {
		StringBuilder respuesta = new StringBuilder();//StringBuilder --> Mejora la concatenación de String
		//StringBuffer respuesta = new StringBuffer();//Similar a StringBuilder pero resistente a threads
		URL url = new URL(strURL);//URL identifica un recurso en internet
		HttpURLConnection conexion = (HttpURLConnection)url.openConnection();//Abrir conexión
		//Configuración de la conexión
		conexion.setRequestMethod("GET");
		//Se pueden añadir más parámetros (tipo de retorno, juego de caracteres, agente de usuario...)
		//Establecer la conexión
		int code = conexion.getResponseCode();
		if (code==HttpURLConnection.HTTP_OK){//HTTP_OK == 200
			System.out.println("La petición se ha procesado correctamente");
			//Abierto el canal de lectura
			Reader reader = new InputStreamReader(conexion.getInputStream());
			//Lectura del stream de entrada carácter a carácter
			int caracter;
			while((caracter=reader.read())!=-1) {
				respuesta.append((char)caracter);
			}
		} else {
			System.err.println("Ha ocurrido un error:" + code);
		}
		conexion.disconnect();//Cerrar conexión
		return respuesta.toString();
	}
}