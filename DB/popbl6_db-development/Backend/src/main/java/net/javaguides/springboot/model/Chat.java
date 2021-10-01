package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "chat")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "userId")
	private User userId;

	@Column(name = "userId2")
	private int userId2;

	public Chat() {

	}

	public Chat(User userId, int userId2) {
		super();
		this.userId = userId;
		this.userId2 = userId2;
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

	/**
	 * @return User
	 */
	public User getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 */
	public void setUserId(User userId) {
		this.userId = userId;
	}

	/**
	 * @return User
	 */
	public int getUserId2() {
		return userId2;
	}

	/**
	 * @param userId
	 */
	public void setUserId2(int userId2) {
		this.userId2 = userId2;
	}

}
