package com.fernandopaniagua.omdbhttpclient;

public class Movie {
	private String title;
	private String year;
	private String director;
	private String plot;
	private String poster;
	
	public Movie(String title, String year, String director, String plot, String poster) {
		super();
		this.title = title;
		this.year = year;
		this.director = director;
		this.plot = plot;
		this.poster = poster;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	


}
