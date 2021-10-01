package net.javaguides.springboot.model;

import java.util.List;

public class Resultados {
	int userId;
	List<String> results;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<String> getResultados() {
		return results;
	}
	public void setResultados(List<String> results) {
		this.results = results;
	}
	public Resultados(int userId, List<String> results) {
		this.userId = userId;
		this.results = results;
	}
	

}
