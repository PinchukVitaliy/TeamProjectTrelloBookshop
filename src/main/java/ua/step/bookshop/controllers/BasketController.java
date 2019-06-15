package ua.step.bookshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.step.bookshop.models.Book;
import ua.step.bookshop.models.User;
import ua.step.bookshop.repositories.UserRepository;

/**
 * 
 * @author sergey Класс выполняет операции над корзиной
 *
 */
@Controller
public class BasketController extends SecurityContextHolder {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param model
	 * @return страница корзины
	 */
	@GetMapping(value = "/basket")
	public String getBasketPage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		model.addAttribute("books", user.getBooks());
		model.addAttribute("contentPage", "basket");
		return "index";
	}

	/**
	 * 
	 * @param book
	 * @return Добавляет книгу в корзину пользователя
	 */
	@PostMapping(value = "/basket/add")
	public String addBookToBasket(@ModelAttribute("book") Book book) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		List<Book> books = user.getBooks();
		books.add(book);
		user.setBooks(books);
		userRepository.save(user);
		return "redirect:/";
	}

	/**
	 * 
	 * @param book
	 * @param model
	 * @return Удаляет книгу из корзины пользователя
	 */
	@PostMapping(value = "/basket/remove")
	public String removeBookFromBasket(@ModelAttribute("book") Book book, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		user.setBooks(user.bookRemove(user.getBooks(), book));
		userRepository.save(user);
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