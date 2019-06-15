package ua.step.bookshop.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.step.bookshop.models.Book;
import ua.step.bookshop.models.BookDTO;
import ua.step.bookshop.models.Favorites;
import ua.step.bookshop.models.MoneyList;
import ua.step.bookshop.models.Rate;
import ua.step.bookshop.models.User;
import ua.step.bookshop.repositories.*;

@Controller
public class BookController {
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private GenreRepository genreRepo;
	@Autowired
	private PublisherRepository publisherRepo;
	@Autowired
	private AuthorRepository authorRepo;
	@Autowired
	private FavouriteRepository favouriteRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RateRepository rateRepository;

	private static int BOOKSONPAGE = 9;

	@GetMapping("books/{page}")
	private String getPagedBooks(Model model, @PathVariable int page) {
		return getBooks(model, page);
	}

	String getBooks(Model model, int page) {
		List<Book> allBooks = bookRepo.findByOrderByCreateDate();
		List<Book> books = new ArrayList<>();
		int pages = (int) Math.ceil((double) allBooks.size() / BOOKSONPAGE);

		for (int i = (page - 1) * BOOKSONPAGE; i < (page) * BOOKSONPAGE && i < allBooks.size(); i++) {
			books.add(allBooks.get(i));
		}
		model.addAttribute("curpage", page);
		model.addAttribute("pages", pages);
		model.addAttribute("books", books);

		return "books";
	}

	@GetMapping("/books/addBook")
	private String addBook(Model model) {
		model.addAttribute("publishers", publisherRepo.findAll());
		model.addAttribute("genres", genreRepo.findAll());
		model.addAttribute("authors", authorRepo.findAll());
		model.addAttribute("contentPage", "addBook");
		return "index";
	}

	@PostMapping("/books/addBook")
	private String addBookSubmit(@ModelAttribute("book") Book book) {
		Short idUs = getAuthUserId(userRepo);
		book.setCreateDate(Calendar.getInstance().getTime());
		book.setUser(userRepo.getOne(idUs));
		bookRepo.saveAndFlush(book);
		return "redirect:/";
	}

	// Константин
	@GetMapping("/books/show/{id}")
	private String showBook(@PathVariable("id") Integer id, Model model) {
		Short idUs = getAuthUserId(userRepo);
		List<Favorites> favoritesList = favouriteRepo.findAll();
		boolean flag = false;
		for (int i = 0; i < favoritesList.size(); i++) {
			if (idUs == favoritesList.get(i).getIdUser() && id == favoritesList.get(i).getIdBook()) {
				flag = true;
			}
		}
		Book book = bookRepo.getOne(id);
		model.addAttribute("flag", flag);
		model.addAttribute("bookInf", book);
		model.addAttribute("userId", idUs);
		model.addAttribute("contentPage", "showBook");
		return "index";
	}

	// Константин
	@PostMapping("/books/addRate")
	private String changeRate(@RequestParam("idbook") Integer idbook,
			@RequestParam(value = "rateOnBook") Integer rate) {
		addRate(rate, idbook, getAuthUserId(userRepo));
		return "redirect:/books/show/" + idbook;
	}

	private void addRate(Integer rate, Integer idbook, Short idUs) {
		Rate newRate = new Rate();
		newRate.setIdBook(idbook);
		newRate.setIdUser(idUs);
		newRate.setRate((double) rate);
		newRate.setReplace(true);
		rateRepository.save(newRate);

		List<Rate> rates = rateRepository.findAll();
		List<Rate> rates2 = new ArrayList<>();
		for (Rate rate2 : rates) {
			boolean rateHasTheSameBook = rate2.getIdBook().equals(idbook);

			if (rateHasTheSameBook) {
				rates2.add(rate2);
			}
		}

		Double d = 0.0;
		for (Rate rate2 : rates2) {
			d += rate2.getRate();
		}

		Book book = new Book();
		book = bookRepo.getOne(idbook);
		book.setRate(d / rates2.size());
		bookRepo.save(book);
	}

	@PostMapping("/books/favourite")
	private String favoriteBook(@RequestParam("idbook") Integer id,
			@RequestParam(value = "check", required = false) String check) {
		Short idUs = getAuthUserId(userRepo);

		if (check != null) {
			Favorites favorites = new Favorites();
			favorites.setIdUser(idUs);
			favorites.setIdBook(id);
			favouriteRepo.save(favorites);
		} else {
			List<Favorites> favoritesList = favouriteRepo.findAll();
			for (int i = 0; i < favoritesList.size(); i++) {

				if (favoritesList.get(i).getIdUser() == idUs && favoritesList.get(i).getIdBook() == id) {
					favouriteRepo.delete(favoritesList.get(i));
				}
			}
		}

		return "redirect:/books/show/" + id;
	}

	@GetMapping("/books/editBook/{id}")
	private String editBook(@PathVariable("id") Integer id, Model model) {
		if (bookRepo.getOne(id).getPublisher() != null) {
			model.addAttribute("publisherChecked", bookRepo.getOne(id).getPublisher());
		} else {
			model.addAttribute("publisherChecked", publisherRepo.getOne((short) 1));
		}
		model.addAttribute("book", bookRepo.getOne(id));
		model.addAttribute("publishers", publisherRepo.findAll());
		model.addAttribute("genres", genreRepo.findAll());
		model.addAttribute("authors", authorRepo.findAll());
		model.addAttribute("genreChecked", bookRepo.getOne(id).getGenres());
		model.addAttribute("authorsChecked", bookRepo.getOne(id).getAuthors());
		model.addAttribute("contentPage", "editBook");
		return "index";

	}

	@PostMapping("/books/editBook")
	private String editBookSubmit(@ModelAttribute("book") Book book) {
		book.setCreateDate(Calendar.getInstance().getTime());
		bookRepo.save(book);
		return "redirect:/";
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