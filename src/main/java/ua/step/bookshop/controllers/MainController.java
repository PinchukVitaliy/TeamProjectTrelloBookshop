package ua.step.bookshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.step.bookshop.models.Book;
import ua.step.bookshop.models.BookDTO;
import ua.step.bookshop.models.MoneyList;
import ua.step.bookshop.models.User;
import ua.step.bookshop.repositories.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private FavouriteRepository favouriteRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private GenreRepository genreRepo;
	@Autowired
	private PublisherRepository publisherRepo;

	private static int BOOKSONPAGE = 9;
	private static String CURRENCYONPAGE = "UAH";

	@GetMapping("/")
	private String getIndex(Model model) {
		return getBooksInPage(model, 1);
	}

	@GetMapping("/{page}")
	private String getPaginatedIndex(Model model, @PathVariable int page) {
		return getBooksInPage(model, page);
	}

	@PostMapping("/")
	private String setCurrencyOnPage(Model model, HttpSession session, HttpServletRequest request) {

		if (request.getParameter("currencyOnPage") != null) {
			MainController.CURRENCYONPAGE = String.valueOf(request.getParameter("currencyOnPage"));
		} else {
			MainController.BOOKSONPAGE = Integer.valueOf(request.getParameter("booksOnPage"));
		}

		return getBooksInPage(model, 1);
	}

	/**
	 * возвращает книги, которые должны отображаться на данной странице
	 * @author Andrew
	 * @param model - модель, в которую вставляются параметры
	 * @param page - номер страницы
	*/
	String getBooksInPage(Model model, int page) {
		List<Book> allBooks = bookRepo.findAll();
		List<BookDTO> allDtoBooks = new ArrayList<>();
		List<BookDTO> books = new ArrayList<>();

		for (Book book : allBooks) {
			BookDTO bookDto = new BookDTO();
			bookDto.setId(book.getId());
			bookDto.setName(book.getName());
			bookDto.setBackground(book.getBackground());
			String price = MoneyList.calcPrice(CURRENCYONPAGE, book.getPrice());
			bookDto.setPrice(price);
			allDtoBooks.add(bookDto);
		}

		int pages = (int) Math.ceil((double) allBooks.size() / BOOKSONPAGE);

		for (int i = (page - 1) * BOOKSONPAGE; i < (page) * BOOKSONPAGE && i < allBooks.size(); i++) {
			books.add(allDtoBooks.get(i));
		}

		model.addAttribute("userId", getAuthUserId(userRepo));

		model.addAttribute("curpage", page);
		model.addAttribute("pages", pages);
		model.addAttribute("books", books);
		model.addAttribute("favorites", favouriteRepo.findAll());
		model.addAttribute("genres", genreRepo.findAll());
		model.addAttribute("publishers", publisherRepo.findAll());
		model.addAttribute("contentPage", "fragments/books");
		return "index";
	}

	@GetMapping("/payment")
	private String getPayment(Model model) {
		model.addAttribute("contentPage", "payment");
		return "index";
	}

	@GetMapping("/delivery")
	private String getDelivery(Model model) {
		model.addAttribute("contentPage", "delivery");
		return "index";
	}

	@GetMapping("/contacts")
	private String getContacts(Model model) {
		model.addAttribute("contentPage", "contacts");
		return "index";
	}

	private Short getAuthUserId(UserRepository repo) {
		Short id = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		if (!name.equals("anonymousUser")) {
			Optional<User> user = repo.findByLogin(name);
			id = user.get().getId();
		}

		return id;
	}
}