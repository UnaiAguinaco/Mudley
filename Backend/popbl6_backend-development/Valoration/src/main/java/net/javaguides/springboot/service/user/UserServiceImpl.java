package net.javaguides.springboot.service.user;

import java.util.Collection;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
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
		userRepository.save(user);
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
		User user = userRepository.getOne(id);
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
