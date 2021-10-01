package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import javax.persistence.JoinColumn;

@Entity
@Component
@Table(name = "valoration")
public class Valoration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "rating")
	private float rating;

	@Column(name = "token")
	private String token;

	@Column(name = "valorator")
	private int valorator;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "userId")
	private User userId;

	public Valoration() {

	}

	public Valoration(float rating, String token, User userId, int valorator) {
		super();
		this.rating = rating;
		this.userId = userId;
		this.token = token;
		this.valorator = valorator;
	}

	/**
	 * @return Long
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getValorator() {
		return valorator;
	}

	public void setValorator(int valorator) {
		this.valorator = valorator;
	}

}
