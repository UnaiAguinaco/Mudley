package net.javaguides.springboot.service.gender;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Gender;

import net.javaguides.springboot.repository.GenderRepository;


@Service
public class GenderServiceImpl implements GenderService {

	@Autowired
	private GenderRepository genderRepository;


	public GenderServiceImpl(GenderRepository genderRepository) {
		super();
		this.genderRepository = genderRepository;
	}


	@Override
	public List<Gender> getAllGenders() {
		return genderRepository.findAll();
	}


	@Override
	public void saveGender(Gender chat) {
		this.genderRepository.save(chat);
		
	}


	@Override
	public void deleteGenderById(int id) {
		this.genderRepository.deleteById(id);
		
	}



	@Override
	public Gender getGenderById(int id) {
		List<Gender> genders = getAllGenders();
		for (Gender gender : genders) {
			if (gender.getId() == id) {
				return gender;
			}
		}
		//User user = userRepository.getOne(id);
		return null;
		
	}




}
