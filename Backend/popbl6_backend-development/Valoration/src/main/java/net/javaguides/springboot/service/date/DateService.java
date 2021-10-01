package net.javaguides.springboot.service.date;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Date;

@Service
public interface DateService {
	List<Date> getAllDates();

	void saveDate(Date date);

	void deleteDateById(int id);

	Date getDateById(int id);

}
