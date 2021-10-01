package net.javaguides.springboot.service.date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Date;

import net.javaguides.springboot.repository.DateRepository;

@Service
public class DateServiceImpl implements DateService {

	@Autowired
	private DateRepository dateRepository;

	public DateServiceImpl(DateRepository dateRepository) {
		super();
		this.dateRepository = dateRepository;
	}

	@Override
	public List<Date> getAllDates() {
		return dateRepository.findAll();
	}

	@Override
	public void saveDate(Date chat) {
		this.dateRepository.save(chat);

	}

	@Override
	public void deleteDateById(int id) {
		this.dateRepository.deleteById(id);

	}

	@Override
	public Date getDateById(int id) {
		return dateRepository.getOne(id);

	}

}
