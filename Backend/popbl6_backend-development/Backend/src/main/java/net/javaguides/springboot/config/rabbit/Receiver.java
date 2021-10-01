package net.javaguides.springboot.config.rabbit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import org.springframework.stereotype.Component;

import net.javaguides.springboot.controller.emailSender;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.EmailService;
import net.javaguides.springboot.service.user.UserService;

@Component
public class Receiver {

  UserService userService;
  EmailService emailService;

  public Receiver(UserService userService, EmailService emailService) {
    this.userService = userService;
    this.emailService = emailService;
  }

  private CountDownLatch latch = new CountDownLatch(1);

  public void receiveMessage(byte[] b) throws IOException {
    String message = new String(b, StandardCharsets.UTF_8);
    Gson gson = new Gson();
    User u = gson.fromJson(message, User.class);
    message = u.getName() + "_" + u.getId();
    System.out.println("Received <" + message + ">");
    List<User> users = userService.getAllUsers();
    List<User> fusers = new ArrayList<>();
    for (User user : users) {
      if (user.getRoles().iterator().next().getName().equals("ORGANIZACION")) {
        fusers.add(user);
      }
    }
    int tamañotrozo = Runtime.getRuntime().availableProcessors();

    if (fusers.size() < tamañotrozo)
      tamañotrozo = 1;
    int numTrozos = fusers.size() / tamañotrozo;
    ExecutorService executor = Executors.newFixedThreadPool(tamañotrozo);
    List<emailSender> senders = new ArrayList<>();
    int inicio = 0;
    int fin = 0;
    for (int i = 0; i < numTrozos; i++) {
      inicio = i * tamañotrozo;
      fin = (i + 1) * tamañotrozo;
      List<User> thUsers = new ArrayList<>();
      for (int j = inicio; j < fin; j++) {
        thUsers.add(fusers.get(j));
      }
      senders.add(new emailSender(thUsers, emailService, message));

    }
    try {
      executor.invokeAll(senders);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}
