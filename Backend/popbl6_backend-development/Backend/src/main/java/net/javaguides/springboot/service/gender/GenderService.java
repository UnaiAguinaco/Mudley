package net.javaguides.springboot.service.gender;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Gender;
@Service
public interface GenderService{
	List<Gender> getAllGenders();

	void saveGender(Gender chat);

	void deleteGenderById(int id);

	Gender getGenderById(int id);

	Gender getGenderByName(String name);
}
