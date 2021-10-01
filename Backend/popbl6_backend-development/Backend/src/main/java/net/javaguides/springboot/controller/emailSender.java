package net.javaguides.springboot.controller;

import java.util.List;
import java.util.concurrent.Callable;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.EmailService;

public class emailSender implements Callable<Void> {

  EmailService emailSender;
  String message;
  List<User> fusers;

  public emailSender(List<User> fusers, EmailService emailSender, String message) {
    this.fusers = fusers;
    this.emailSender = emailSender;
    this.message = message;
  }

  @Override
  public Void call() throws Exception {
    for (User user : fusers) {

      emailSender.sendMail(message, user.getEmail());
    }
    return null;
  }

}

