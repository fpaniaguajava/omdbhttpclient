package com.fernandopaniagua.omdbhttpclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static j2html.TagCreator.*;

public class App {

	private static final String API_KEY = null;

	public static void main(String[] args) {
		if (API_KEY==null) {
			System.err.println("Por favor, obten un API_KEY de https://www.omdbapi.com/ y asigna el valor en la linea 16");
			System.exit(-1);
		}
		new App().ejecutar();
	}

	public void ejecutar() {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Indroduce el título de la película:");
			String titulo = sc.nextLine();
			String strURL = String.format("http://www.omdbapi.com/?apikey=%s&t=%s", API_KEY, titulo);
			String strJSON = ClienteHTTP.getStringFromURL(strURL);
			System.out.println(strJSON);
			System.out.println("La información en formato JSON ha sido recibida");
			Movie movie = getMovie(strJSON);
			System.out.print("¿Metodo de generación? Fernando[0] Miguel[1]:");
			int metodo = sc.nextInt();
			if (metodo==0) {
				generateHTML(movie);
			} else {
				generateHTMLWithJ2HTML(movie);
			}
			System.out.println("La página ha sido generada correctamente");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		sc.close();
	}

	public static Movie getMovie(String strPelicula) throws Exception {
		Movie movie = null;
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonPelicula = (JSONObject) parser.parse(strPelicula);
			if (jsonPelicula.containsKey("Error")) {
				throw new Exception((String) jsonPelicula.get("Error"));
			}
			System.out.println("JSON proceso completo");
			String title = (String) jsonPelicula.get("Title");
			String year = (String) jsonPelicula.get("Year");
			String director = (String) jsonPelicula.get("Director");
			String plot = (String) jsonPelicula.get("Plot");
			String poster = (String) jsonPelicula.get("Poster");
			movie = new Movie(title, year, director, plot, poster);

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return movie;
	}

	public static void generateHTML(Movie movie) throws FileNotFoundException {
		String ruta = "F:/peliculas/";
		String plantilla = "<!DOCTYPE html>" + "<html lang='es'>" + "<head>" + "    <meta charset='UTF-8'>"
				+ "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>"
				+ "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>"
				+ "    <link rel='stylesheet' href='movie.css'>" + "    <title>%s</title>" + "</head>" + "<body>"
				+ "    <article>" + "        <div class='titulo'>%s</div>" + "        <div class='anyo'>%s</div>"
				+ "        <div class='director'>%s</div>" + "        <div class='sinopsis'>%s</div>"
				+ "        <div class='poster'><img src='%s'></div>" + "    </article>" + "</body>" + "</html>";
		String htmlCode = String.format(plantilla, movie.getTitle(), movie.getTitle(), movie.getYear(),
				movie.getDirector(), movie.getPlot(), movie.getPoster());
		String fileName = ruta + movie.getTitle().replace(' ', '-').replace(':', '-') + ".html";
		PrintWriter pw = new PrintWriter(new File(fileName));
		pw.write(htmlCode);
		pw.close();
	}

	public static void generateHTMLWithJ2HTML(Movie movie) throws FileNotFoundException {
		String ruta = "F:/peliculas/";

		String htmlCode = 
				html(
					head(title(movie.getTitle()), 
							link().withRel("stylesheet").withHref("./movie.css")
					),
					body(
						article(
							h1(movie.getTitle()).withClass("titulo"), 
							h2("Director:" + movie.getDirector()).withClass("director"),
							h2("Año:" + movie.getYear()).withClass("anyo"), 
							h2("Trama:" + movie.getPlot()).withClass("sinopsis"), 
							div(img().withSrc(movie.getPoster())).withClass("poster")
						)
					)
				)
				.render();

		String pageHTML = ruta + movie.getTitle() + ".html";
		PrintWriter pw;
		pw = new PrintWriter(pageHTML);
		pw.println(htmlCode);
		pw.close();

	}
}
