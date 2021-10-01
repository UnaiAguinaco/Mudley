package net.javaguides.springboot.controller.dto;

public class UserRegistrationDto {
	private String name;
	private String email;
	private String password;
	private String image;
	private String budget;
	private String country;
	private String city;
	private int cp;
	private String description;
	private String genderId;
	private String link;
	private boolean artista;
	private boolean organizacion;

	public UserRegistrationDto() {

	}

	public UserRegistrationDto(String name, String email, String password, String image, String budget, String country,
			String city, int cp, String link, String description, String genderId, boolean artista, boolean organizacion) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = image;
		this.budget = budget;
		this.country = country;
		this.city = city;
		this.cp = cp;
		this.description = description;
		this.genderId = genderId;
		this.link = link;
		this.artista = artista;
		this.organizacion = organizacion;
	}

	/**
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean getArtista() {
		return artista;
	}

	public void setArtista(boolean artista) {
		this.artista = artista;
	}

	public boolean getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(boolean organizacion) {
		this.organizacion = organizacion;
	}

}
