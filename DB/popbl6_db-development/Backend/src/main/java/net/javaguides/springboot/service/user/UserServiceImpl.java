package net.javaguides.springboot.service.user;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
		return userRepository.findAll();
	}

	/**
	 * @param user
	 */
	@Override
	public void saveUser(User user) {
		this.userRepository.save(user);
	}

	/**
	 * @param registrationDto
	 * @return User
	 */
	@Override
	public User save(UserRegistrationDto registrationDto) {
		if (registrationDto.getArtista()) {
			User user = new User(registrationDto.getName(), registrationDto.getEmail(),
					passwordEncoder.encode(registrationDto.getPassword()), "img/artistExample.jpg", registrationDto.getBudget(),
					registrationDto.getCountry(), registrationDto.getCity(), registrationDto.getCv(), registrationDto.getLink(),
					registrationDto.getDescription(),
					genderService.getGenderById(Integer.parseInt(registrationDto.getGenderId())),
					Arrays.asList(new Role("ARTISTA")));
			System.out.println(user);
			return userRepository.save(user);
		} else if (registrationDto.getOrganizacion()) {
			User user = new User(registrationDto.getName(), registrationDto.getEmail(),
					passwordEncoder.encode(registrationDto.getPassword()), "img/artistExample.jpg", registrationDto.getBudget(),
					registrationDto.getCountry(), registrationDto.getCity(), registrationDto.getCv(), registrationDto.getLink(),
					registrationDto.getDescription(),
					genderService.getGenderById(Integer.parseInt(registrationDto.getGenderId())),
					Arrays.asList(new Role("ORGANIZACION")));
			System.out.println(user);
			return userRepository.save(user);
		}
		return null;
	}

	/**
	 * @param idal
	 */
	@Override
	public void deleteUserByIdal(int idal) {
		this.userRepository.deleteById(idal);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.getUserByEmail(email);
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
		List<User> users = getAllUsers();
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		//User user = userRepository.getOne(id);
		return null;
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
