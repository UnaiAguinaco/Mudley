package net.javaguides.springboot.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import Conexiones.Conexion;
import net.javaguides.springboot.controller.dto.UserRegistrationDto;
import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.gender.GenderService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private GenderService genderService;

	public UserServiceImpl(UserRepository userRepository, GenderService genderService) {
		super();
		this.userRepository = userRepository;
		this.genderService = genderService;
	}

	/**
	 * @return List<User>
	 */
	@Override
	public List<User> getAllUsers() {
		Conexion c= new Conexion();
	    String payload=c.guardarUsuario("getAll", null,"POST");
		Gson gson = new Gson();
		User[] users = gson.fromJson(payload, User[].class);
		List<User> lista=new ArrayList<>();
		for (User u: users) {
		   lista.add(u);
		}
		return lista;
	}

	/**
	 * @param user
	 */
	@Override
	public void saveUser(User user) {
		Conexion c= new Conexion();
		Gson gson = new Gson();
		String json = gson.toJson(user);
	    c.guardarUsuario("guardarUsuario", json,"POST");
	   
	}

	/**
	 * @param registrationDto
	 * @return User
	 */
	@Override
	public User save(UserRegistrationDto registrationDto) {
		if (!registrationDto.getArtista() && !registrationDto.getOrganizacion()) {
			registrationDto.setOrganizacion(true);
		}
		if (registrationDto.getBudget().equals("")) {
			registrationDto.setBudget("0");
		}
		Conexion c= new Conexion();
		if (registrationDto.getArtista()) {
			User user = new User(registrationDto.getName(), registrationDto.getEmail(),
					passwordEncoder.encode(registrationDto.getPassword()), "img/artistExample.jpg",
					Float.parseFloat(registrationDto.getBudget()), registrationDto.getCountry(), registrationDto.getCity(),
					registrationDto.getCp(), registrationDto.getLink(), registrationDto.getDescription(),
					genderService.getGenderById(Integer.parseInt(registrationDto.getGenderId())),
					Arrays.asList(new Role("ARTISTA")));
			Gson gson = new Gson();
			String json = gson.toJson(registrationDto);
		    c.guardarUsuario("guardarUsuario", json,"POST");
			Gson gso = new Gson();
			String jso = gson.toJson(user);
		    c.guardarUsuario("registro", jso,"POST");
			return user;
		} else if (registrationDto.getOrganizacion()) {
			User user = new User(registrationDto.getName(), registrationDto.getEmail(),
					passwordEncoder.encode(registrationDto.getPassword()), "img/artistExample.jpg",
					Float.parseFloat(registrationDto.getBudget()), registrationDto.getCountry(), registrationDto.getCity(),
					registrationDto.getCp(), registrationDto.getLink(), registrationDto.getDescription(),
					genderService.getGenderById(Integer.parseInt(registrationDto.getGenderId())),
					Arrays.asList(new Role("ORGANIZACION")));
			Gson gson = new Gson();
			String json = gson.toJson(registrationDto);
		    c.guardarUsuario("guardarUsuario", json,"POST");
			Gson gso = new Gson();
			String jso = gson.toJson(user);
		    c.guardarUsuario("registro", jso,"POST");
			return user;
		}
		return null;
	}

	/**
	 * @param idal
	 */
	@Override
	public void deleteUserByIdal(int idal) {
		Conexion c= new Conexion();
	    c.guardarUsuario("delete/"+idal+"", null,"DELETE");
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = getUserByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	/**
	 * @param roles
	 * @return Collection<? extends GrantedAuthority>
	 */
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	/**
	 * @param id
	 * @return User
	 */
	@Override
	public User getUserByIdal(int id) {
		Conexion c= new Conexion();
		String result;
		String json= "{ \"id\":"+id+" }";
	    result=c.guardarUsuario("getId", json,"POST");
	    Gson gson = new Gson();
		User user = gson.fromJson(result, User.class);
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		List<User> users = getAllUsers();
		for (User user : users) {
			if (user.getEmail() != null) {
				if (user.getEmail().equals(email)) {
					return user;
				}
			}
		}
		return null;

	}

}
