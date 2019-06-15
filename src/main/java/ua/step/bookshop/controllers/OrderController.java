package ua.step.bookshop.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.step.bookshop.models.Book;
import ua.step.bookshop.models.User;
import ua.step.bookshop.repositories.UserRepository;

@Controller
public class OrderController extends SecurityContextHolder {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/order")
	private String getOrder(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		List<Book> books = user.getBooks();
		int price = 0;
		for (int i = 0; i < books.size(); i++) {
			price += books.get(i).getPrice();
		}
		model.addAttribute("book", user.getBooks());
		model.addAttribute("price", price);
		model.addAttribute("contentPage", "basket");
		model.addAttribute("contentPage", "order");
		return "index";
	}
}