package net.javaguides.springboot.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import net.javaguides.springboot.model.Valoration;
import net.javaguides.springboot.model.Date;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.gender.GenderService;
import net.javaguides.springboot.service.user.UserService;
import net.javaguides.springboot.service.valoration.ValorationService;
import net.javaguides.springboot.service.EmailService;
import net.javaguides.springboot.service.date.DateService;

@Controller
public class ValorationChange {
	UserService userService;
	GenderService genderService;
	DateService dateService;
	EmailService emailService;
	ValorationService valorationService;

	public ValorationChange(UserService userService, GenderService genderService, DateService dateService,
			ValorationService valorationService, EmailService emailService) {
		this.userService = userService;
		this.genderService = genderService;
		this.dateService = dateService;
		this.emailService = emailService;
		this.valorationService = valorationService;
		List<Date> dates = dateService.getAllDates();
		List<Date> yesterday = new ArrayList<>();
		List<User> allUsers = userService.getAllUsers();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.UK)
				.withZone(ZoneId.systemDefault());
		Instant now = Instant.now();
		Instant yesteDate = now.minus(1, ChronoUnit.DAYS);
		String sDate = formatter.format(yesteDate).split(",")[0];
		int day = Integer.valueOf(sDate.split(" ")[0]);
		String month = sDate.split(" ")[1];
		int year = Integer.valueOf(sDate.split(" ")[2]);
		for (Date date : dates) {
			if (date.getDay() == day && date.getMonth().equals(month) && date.getYear() == year && date.getRecruiter() != 0) {
				yesterday.add(date);
			}
		}
		for (Date date : yesterday) {
			User recruiter = new User();
			for (User user : allUsers) {
				if (date.getRecruiter() == user.getId()) {
					recruiter = user;
					break;
				}
			}
			Valoration valoration = new Valoration(Float.parseFloat("0"), UUID.randomUUID().toString(), date.getUserId(),
					date.getRecruiter());
			emailService
					.sendMail(
							"https://mudley.duckdns.org/valoration" + valoration.getToken() + "_" + valoration.getUserId().getId() + "_"
									+ valoration.getValorator(),
							valoration.getUserId().getName(), recruiter.getName(), recruiter.getEmail());
			valorationService.saveValoration(valoration);
		}

	}

}
