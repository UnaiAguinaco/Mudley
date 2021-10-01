package net.javaguides.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import IAgent.Agente;
import net.javaguides.springboot.model.AjustesAvanzados;
import net.javaguides.springboot.model.Busqueda;
import net.javaguides.springboot.model.Chat;
import net.javaguides.springboot.model.Gender;
import net.javaguides.springboot.model.Message;
import net.javaguides.springboot.model.Resultados;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.model.Valoration;
import net.javaguides.springboot.service.chat.ChatService;
import net.javaguides.springboot.service.date.DateService;
import net.javaguides.springboot.service.gender.GenderService;
import net.javaguides.springboot.service.message.MessageService;
import net.javaguides.springboot.service.user.UserService;
import net.javaguides.springboot.service.valoration.ValorationService;

@Controller
public class UserController {

	UserService userService;
	GenderService genderService;
	ChatService chatService;
	MessageService messageService;
	DateService dateService;
	ValorationService valorationService;
	BCryptPasswordEncoder passwordEncoder;

	public UserController(UserService userService, GenderService genderService, ChatService chatService,
			MessageService messageService, BCryptPasswordEncoder passwordEncoder, DateService dateService,
			ValorationService valorationService) {
		this.userService = userService;
		this.genderService = genderService;
		this.chatService = chatService;
		this.messageService = messageService;
		this.passwordEncoder = passwordEncoder;
		this.dateService = dateService;
		this.valorationService = valorationService;
	}

	@GetMapping("/MainMenu")
	public String index(HttpServletRequest request, Model model) {
		if (request.getUserPrincipal() == null) {
			return "index";
		} else {
			model.addAttribute("busqueda", new Busqueda("", "", "", "", ""));
			List<Gender> list = genderService.getAllGenders();
			model.addAttribute("generos", list);
			User user = userService.getUserByEmail(request.getUserPrincipal().getName());
			Agente a=new Agente(null,user);
			a.setAccion(0);
			a.obtenerRecomendaciones(null, user);
			List<User> results = new ArrayList<>();
			Resultados resultados=a.getResultado();
			int i=0;
			for(String r:resultados.getResultados()) {		
				String[] lista= resultados.getResultados().get(i).split("[']");
				String[] palabras=lista[1].split("[ ]");
				results.add(new User(palabras[1],"", "", "./img/prueba/"+palabras[1]+".jpg",Float.parseFloat(palabras[palabras.length-2]), "Espa�a",palabras[palabras.length-1],02025, "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X", "", genderService.getGenderByName(palabras[palabras.length-3]), null));
				i++;
			}
			List<User> artistas = new ArrayList<>();		
			// for each con todos los ids
			try {
				for(int j=0;j<12;j++) {
					artistas.add(results.get(j));
				}		
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			model.addAttribute("recomendados", artistas);
			//////////////////////////
			Agente b=new Agente(null,user);
			b.setAccion(1);
			b.obtenerRecomendaciones(null, user);
			results = new ArrayList<>();
			resultados=b.getResultado();
			for(String r:resultados.getResultados()) {
				results.add(new User(r,"", "", "./img/prueba/"+r+".jpg",Float.parseFloat("200"), "Espa�a","Zambrana",02025, "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X", "", genderService.getGenderById(1), null));
			}
			List<User> art = new ArrayList<>();		
			// for each con todos los ids
			try {
				for(int j=0;j<12;j++) {
					art.add(results.get(j));
				}		
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			model.addAttribute("emergentes", art);
			/////////////////////////////
			Agente c=new Agente(null,user);
			c.setAccion(2);
			c.obtenerRecomendaciones(null, user);
			results = new ArrayList<>();
			resultados=c.getResultado();
			i=0;
			for(String r:resultados.getResultados()) {		
				String[] lista= resultados.getResultados().get(i).split("[']");
				String[] palabras=lista[1].split("[ ]");
				results.add(new User(palabras[1],"", "", "./img/prueba/"+palabras[1]+".jpg",Float.parseFloat(palabras[palabras.length-2]), "Espa�a",palabras[palabras.length-1],02025, "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X", "", genderService.getGenderByName(palabras[palabras.length-3]), null));
				i++;
			}
			List<User> arti = new ArrayList<>();		
			// for each con todos los ids
			try {
				for(int j=0;j<12;j++) {
					arti.add(results.get(j));
				}		
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			model.addAttribute("vuelves", arti);
			//////////////////////////////////////////////////////////
			Agente d=new Agente(null,user);
			d.setAccion(3);
			d.obtenerRecomendaciones(null, user);
			results = new ArrayList<>();
			resultados=d.getResultado();
			i=0;
			for(String r:resultados.getResultados()) {		
				String[] lista= resultados.getResultados().get(i).split("[']");
				String[] palabras=lista[1].split("[ ]");
				results.add(new User(palabras[1],"", "", "./img/prueba/"+palabras[1]+".jpg",Float.parseFloat(palabras[palabras.length-2]), "Espa�a",palabras[palabras.length-1],02025, "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X", "", genderService.getGenderByName(palabras[palabras.length-3]), null));
				i++;
			}
			List<User> artic = new ArrayList<>();		
			// for each con todos los ids
			try {
				for(int j=0;j<12;j++) {
					artic.add(results.get(j));
				}		
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			model.addAttribute("nuestros", artic);

			return "mainMenu";
		}
	}

	@GetMapping("/profile")
	public String profile(HttpServletRequest request, Model model) {
		User user = userService.getUserByEmail(request.getUserPrincipal().getName());
		model.addAttribute("user", user);
		List<net.javaguides.springboot.model.Date> todasFechas = dateService.getAllDates();
		List<String> fechas = new ArrayList<>();
		for (net.javaguides.springboot.model.Date date : todasFechas) {
			if (date.getUserId().getId() == userService.getUserByEmail(request.getUserPrincipal().getName()).getId()) {
				fechas.add(date.getDay() + " " + date.getMonth() + " " + date.getYear());
			}
		}
		List<Valoration> allVals = valorationService.getAllValorations();
		int val = 0;
		for (Valoration valoration : allVals) {
			if (valoration.getUserId().getId() == user.getId()) {
				val++;
			}
		}
		model.addAttribute("valCount", val);
		model.addAttribute("fechas", fechas);
		return "userProfile";
	}

	@PostMapping("/buscar")
	public String buscar(@ModelAttribute("busqueda") Busqueda buscar, Model model) throws UnsupportedEncodingException {
		return "redirect:/resultados" + URLEncoder.encode(buscar.getCity(), "UTF-8") + "_" + buscar.getFecha() + "_"
				+ buscar.getGender() + "_" + buscar.getMin();
	}

	@GetMapping("/resultados{busqueda}")
	public String resultados(@PathVariable(value = "busqueda") String busqueda, Model model,HttpServletRequest request) {
		User user = userService.getUserByEmail(request.getUserPrincipal().getName());
		String busquedaSplit[] = new String[4];
		busquedaSplit = busqueda.split("_");
		int min = Integer.parseInt(busquedaSplit[3]) - 100;
		if (min < 0) {
			min = 0;
		}
		int max = Integer.parseInt(busquedaSplit[3]) + 100;
		Busqueda busqueda2 = new Busqueda(busquedaSplit[0].replace("+", " "), busquedaSplit[1], String.valueOf(min),
				String.valueOf(max), busquedaSplit[2]);
		busqueda2.setGenderName(genderService.getGenderById(Integer.parseInt(busquedaSplit[2])).getName());
		Agente a=new Agente(busqueda2,user);
		a.obtenerRecomendaciones(busqueda2, user);
		List<User> results = new ArrayList<>();
		Resultados resultados=a.getResultado();
		int i=0;
		for(String r:resultados.getResultados()) {
			String[] lista = r.split("[$]");
			results.add(new User(lista[0],"", "", "./img/prueba/"+lista[0],Float.parseFloat(lista[2]), "España",lista[1],02025, "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X", "", genderService.getGenderByName(lista[3]), null));
			i++;
		}
		model.addAttribute("artistas", results);

		model.addAttribute("busqueda", busqueda2);
		List<Valoration> allVals = valorationService.getAllValorations();
		List<Integer> val = new ArrayList<>();

		for (User artista : results) {
			int count = 0;
			for (Valoration valoration : allVals) {
				if (valoration.getUserId().getId() == artista.getId()) {
					count++;
				}
			}
			val.add(count);
		}

		model.addAttribute("valCount", val);
		return "resultados";
	}

	@GetMapping("/resultado{id}")
	public String resultado(@PathVariable(value = "id") int id, Model model) {
		User user = userService.getUserByIdal(id);
		model.addAttribute("user", user);
		List<net.javaguides.springboot.model.Date> todasFechas = dateService.getAllDates();
		List<String> fechas = new ArrayList<>();
		for (net.javaguides.springboot.model.Date date : todasFechas) {
			if (date.getUserId().getId() == user.getId()) {
				fechas.add(date.getDay() + " " + date.getMonth() + " " + date.getYear());
			}
		}
		List<Valoration> allVals = valorationService.getAllValorations();
		int val = 0;
		for (Valoration valoration : allVals) {
			if (valoration.getUserId().getId() == user.getId()) {
				val++;
			}
		}
		model.addAttribute("valCount", val);
		model.addAttribute("fechas", fechas);
		return "profileBusqueda";
	}

	@GetMapping("/ajustes")
	public String ajustes(HttpServletRequest request, Model model) {
		User user = userService.getUserByEmail(request.getUserPrincipal().getName());
		model.addAttribute("user", user);
		List<Gender> list = genderService.getAllGenders();
		model.addAttribute("generos", list);

		List<net.javaguides.springboot.model.Date> todasFechas = dateService.getAllDates();
		List<String> fechas = new ArrayList<>();
		for (net.javaguides.springboot.model.Date date : todasFechas) {
			if (date.getUserId().getId() == userService.getUserByEmail(request.getUserPrincipal().getName()).getId()) {
				fechas.add(date.getDay() + " " + date.getMonth() + " " + date.getYear());
			}
		}
		model.addAttribute("fechas", fechas);
		return "ajustes";
	}

	@GetMapping("/guardarFechas{fechas}")
	public String guardarFechas(@PathVariable(value = "fechas") String fechas, Model model, HttpServletRequest request) {
		if (fechas.equals("deleteAll")) {
			for (net.javaguides.springboot.model.Date date : dateService.getAllDates()) {
				if (date.getUserId().getId() == userService.getUserByEmail(request.getUserPrincipal().getName()).getId()) {
					dateService.deleteDateById(date.getId());

				}
			}
			model.addAttribute("response", "Fechas Guardadas.");
			return "/ajustes:: calendarSaved ";
		}
		String f[] = fechas.split("&");
		boolean exist = false;
		List<net.javaguides.springboot.model.Date> todasFechas = dateService.getAllDates();
		for (String string : f) {
			for (net.javaguides.springboot.model.Date date : todasFechas) {
				exist = false;
				if (Integer.valueOf(string.split("-")[0]) == date.getDay() && string.split("-")[1].equals(date.getMonth())
						&& Integer.valueOf(string.split("-")[2]) == date.getYear()
						&& date.getUserId().getId() == userService.getUserByEmail(request.getUserPrincipal().getName()).getId()) {
					exist = true;
					break;
				}

			}
			if (!exist) {
				dateService.saveDate(new net.javaguides.springboot.model.Date(Integer.valueOf(string.split("-")[2]),
						string.split("-")[1], Integer.valueOf(string.split("-")[0]),
						userService.getUserByEmail(request.getUserPrincipal().getName())));
			}

		}

		if (f.length < dateService.getAllDates().size()) {
			for (net.javaguides.springboot.model.Date date : todasFechas) {
				for (String string : f) {
					exist = false;
					if (Integer.valueOf(string.split("-")[0]) == date.getDay() && string.split("-")[1].equals(date.getMonth())
							&& Integer.valueOf(string.split("-")[2]) == date.getYear()
							&& date.getUserId().getId() == userService.getUserByEmail(request.getUserPrincipal().getName()).getId()) {
						exist = true;

						break;
					}

				}
				if (!exist) {
					dateService.deleteDateById(date.getId());
				}

			}
		}
		model.addAttribute("response", "Fechas Guardadas.");
		return "/ajustes:: calendarSaved ";
	}

	@GetMapping("/ajustesAvanzados")
	public String ajuestesAvanzado(HttpServletRequest request, Model model) {
		AjustesAvanzados ajusteAvanzados = new AjustesAvanzados("", "", "", "");
		model.addAttribute("advanced", ajusteAvanzados);
		return "ajustesAvanzados";
	}

	@GetMapping("/cambiarFoto")
	public String cambiarFoto(HttpServletRequest request, Model model) {
		User user = userService.getUserByEmail(request.getUserPrincipal().getName());
		model.addAttribute("user", user);
		return "cambiarFoto";
	}

	@PostMapping("/fotoCambiada")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		byte[] bytes;
		try {
			File carpeta = new File(".//src//main//resources//static//img//" + request.getUserPrincipal().getName());
			carpeta.mkdir();
			for (String s : carpeta.list()) {
				File currentFile = new File(carpeta.getPath(), s);
				currentFile.delete();
			}
			bytes = file.getBytes();
			Path path = Paths.get(carpeta.getAbsolutePath() + "//" + file.getOriginalFilename());
			Files.write(path, bytes);
			File foto[] = carpeta.listFiles();
			User user = userService.getUserByEmail(request.getUserPrincipal().getName());
			user.setImage("img/" + request.getUserPrincipal().getName() + "/" + foto[0].getName());
			userService.saveUser(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/ajustes";
	}

	@PostMapping("/changeAjustes")
	public String changeAjustes(@ModelAttribute("user") User user, HttpServletRequest request) {

		User actualUser = userService.getUserByEmail(request.getUserPrincipal().getName());
		actualUser.setName(user.getName());
		actualUser.setGenderId(user.getGenderId());
		actualUser.setBudget(user.getBudget());
		actualUser.setCountry(user.getCountry());
		actualUser.setCity(user.getCity());
		actualUser.setCp(user.getCp());
		actualUser.setDescription(user.getDescription());
		actualUser.setLink(user.getLink());
		userService.saveUser(actualUser);

		return "redirect:/profile";
	}

	@PostMapping("/changeAdvanced")
	public String changeAdvanced(@ModelAttribute("advanced") AjustesAvanzados advanced, HttpServletRequest request) {

		User user = userService.getUserByEmail(request.getUserPrincipal().getName());
		if (passwordEncoder.matches(advanced.getActualPass(), user.getPassword())
				&& (advanced.getNewPass().equals(advanced.getNewPassR()))) {
			user.setPassword(passwordEncoder.encode(advanced.getNewPass()));
			user.setEmail(advanced.getEmail());
			userService.saveUser(user);
		}

		return "redirect:/profile";
	}

	@GetMapping("/chats")
	public String chats(HttpServletRequest request, Model model) {
		List<Chat> allChats = chatService.getAllChats();
		List<Chat> userChats = new ArrayList<>();
		List<User> otherUser = new ArrayList<>();
		List<Message> lastMsgList = new ArrayList<>();
		List<Integer> sinLeer = new ArrayList<>();
		int thisUserId = userService.getUserByEmail(request.getUserPrincipal().getName()).getId();

		for (Chat chat : allChats) {
			if (chat.getUserId2() == thisUserId) {
				userChats.add(chat);
			}
			if (chat.getUserId().getId() == thisUserId) {
				userChats.add(chat);
				otherUser.add(userService.getUserByIdal(chat.getUserId2()));
			}
		}

		List<Message> allMsg = messageService.getAllMessages();
		for (Chat chat : userChats) {
			List<Message> filteredMsg = new ArrayList<>();
			for (Message message : allMsg) {
				if (message.getChatId().getId() == chat.getId()) {
					if (message.getText().length() > 10) {
						message.setText(message.getText().substring(0, 10) + "...");
					}

					filteredMsg.add(message);
				}
			}
			if (filteredMsg.size() > 0) {
				lastMsgList.add(filteredMsg.get(filteredMsg.size() - 1));
			} else if (filteredMsg.size() == 0) {
				lastMsgList.add(new Message("no hay mensajes", new Date(), new User(), new Chat(), false, false, ""));
			}
			int count = 0;
			for (Message message : filteredMsg) {
				if (!message.isLeido() && message.getUserId().getId() != thisUserId) {
					count++;
				}
			}
			sinLeer.add(count);
			filteredMsg = new ArrayList<>();
		}

		model.addAttribute("chats", userChats);
		model.addAttribute("otherUsers", otherUser);
		model.addAttribute("lastMsg", lastMsgList);
		model.addAttribute("sinLeer", sinLeer);
		model.addAttribute("msg", new Message());
		return "chat";
	}

	@GetMapping("/newChat{id}")
	public String newChat(@PathVariable(value = "id") int id, HttpServletRequest request, Model model) {
		List<Chat> allChats = chatService.getAllChats();
		int thisUserId = userService.getUserByEmail(request.getUserPrincipal().getName()).getId();
		for (Chat chat : allChats) {
			if (chat.getUserId2() == id && chat.getUserId().getId() == thisUserId) {
				return "redirect:/chats";
			}
		}
		chatService.saveChat(new Chat(userService.getUserByEmail(request.getUserPrincipal().getName()), id));
		return "redirect:/chats";
	}

	@GetMapping("/chat{id}")
	public String abrirChat(Model model, @PathVariable(value = "id") int id, HttpServletRequest request) {
		List<Message> allMessages = messageService.getAllMessages();
		List<Message> filteredMessages = new ArrayList<>();
		for (Message message : allMessages) {
			if (message.getChatId().getId() == id) {
				if (message.getUserId().getId() != userService.getUserByEmail(request.getUserPrincipal().getName()).getId()) {
					message.setLeido(true);
					messageService.saveMessage(message);
				}

				filteredMessages.add(message);
			}
		}
		model.addAttribute("mensajes", filteredMessages);

		model.addAttribute("thisUserId", userService.getUserByEmail(request.getUserPrincipal().getName()).getId());
		return "/chat:: mensajes ";
	}

	@GetMapping("/sendMsg{msg}")
	public String sendMsg(@PathVariable(value = "msg") String msg, Model model, HttpServletRequest request) {
		String txt = msg.split("_")[0].replace("%20", " ");
		txt = txt.replace("%3F", "?");
		int id = Integer.valueOf(msg.split("_")[1]);
		Message newMsg = new Message(txt, new Date(), userService.getUserByEmail(request.getUserPrincipal().getName()),
				chatService.getChatById(id), false, false, "");
		messageService.saveMessage(newMsg);
		List<Message> allMessages = messageService.getAllMessages();
		List<Message> filteredMessages = new ArrayList<>();
		for (Message message : allMessages) {
			if (message.getChatId().getId() == id) {
				filteredMessages.add(message);
			}
		}
		model.addAttribute("mensajes", filteredMessages);

		model.addAttribute("thisUserId", userService.getUserByEmail(request.getUserPrincipal().getName()).getId());
		return "/chat:: mensajes ";
	}

	@GetMapping("/sendPrp{msg}")
	public String sendPrp(@PathVariable(value = "msg") String msg, Model model, HttpServletRequest request) {
		String txt = "Fecha del concierto: " + msg.split("_")[0];
		int id = Integer.valueOf(msg.split("_")[1]);
		Message newMsg = new Message(txt, new Date(), userService.getUserByEmail(request.getUserPrincipal().getName()),
				chatService.getChatById(id), false, true, "noAns");
		messageService.saveMessage(newMsg);
		List<Message> allMessages = messageService.getAllMessages();
		List<Message> filteredMessages = new ArrayList<>();
		for (Message message : allMessages) {
			if (message.getChatId().getId() == id) {
				filteredMessages.add(message);
			}
		}
		model.addAttribute("mensajes", filteredMessages);

		model.addAttribute("thisUserId", userService.getUserByEmail(request.getUserPrincipal().getName()).getId());
		return "/chat:: mensajes ";
	}

	@GetMapping("/respPrp{msg}")
	public String respPrp(@PathVariable(value = "msg") String msg, Model model, HttpServletRequest request) {
		String txt = msg.split("_")[0];
		int id = Integer.valueOf(msg.split("_")[1]);
		String date = msg.split("_")[2];
		Message newMsg = messageService.getMessageById(id);
		newMsg.setAcepted(txt);
		if (txt.equals("accept")) {
			Chat thisChat = newMsg.getChatId();
			net.javaguides.springboot.model.Date fecha = new net.javaguides.springboot.model.Date(
					Integer.valueOf(date.split("-")[0]),
					new DateFormatSymbols(Locale.ENGLISH).getMonths()[Integer.valueOf(date.split("-")[1]) - 1],
					Integer.valueOf(date.split("-")[2]), userService.getUserByIdal(thisChat.getUserId2()));
			fecha.setRecruiter(thisChat.getUserId().getId());
			dateService.saveDate(fecha);
		}
		messageService.saveMessage(newMsg);
		List<Message> allMessages = messageService.getAllMessages();
		List<Message> filteredMessages = new ArrayList<>();
		for (Message message : allMessages) {
			if (message.getChatId().getId() == id) {
				filteredMessages.add(message);
			}
		}
		model.addAttribute("mensajes", filteredMessages);

		model.addAttribute("thisUserId", userService.getUserByEmail(request.getUserPrincipal().getName()).getId());
		return "/chat:: mensajes ";
	}

	@GetMapping("/valoration{str}")
	public String valoration(@PathVariable(value = "str") String str, HttpServletRequest request, Model model) {
		List<Valoration> allVal = valorationService.getAllValorations();
		String token = str.split("_")[0];
		String artist = str.split("_")[1];
		String org = str.split("_")[2];
		String rating = str.split("_")[3];
		boolean exist = false;
		for (Valoration val : allVal) {
			if (val.getToken().equals(token)) {
				exist = true;
				val.setRating(Float.parseFloat(rating));
				val.setToken("");
				valorationService.saveValoration(val);
			}
		}
		if (!exist) {
			model.addAttribute("txt", "Esta valoración no existe.");
			return "valoration";
		}
		int count = 0;
		int valSum = 0;
		for (Valoration valoration : allVal) {
			if (valoration.getUserId().getId() == Integer.valueOf(artist)) {
				count++;
				valSum += valoration.getRating();
			}
		}
		User user = userService.getUserByIdal(Integer.valueOf(artist));
		Float resultado = (float) (valSum / count);
		user.setRating(resultado);
		model.addAttribute("txt",
				"Gracias " + userService.getUserByIdal(Integer.valueOf(org)).getName() + " por tu tiempo.");
		userService.saveUser(user);
		return "valoration";
	}

}
