package ua.step.bookshop.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.step.bookshop.models.Book;
import ua.step.bookshop.models.Favorites;
import ua.step.bookshop.models.User;
import ua.step.bookshop.repositories.*;

@Controller
public class FavouritesController {
	@Autowired
	private BookRepository repo;
	@Autowired
	private FavouriteRepository repoF;
	@Autowired
	private UserRepository repoU;

	@GetMapping("/favourites")
	private String getFavorites(Model model) {
		model.addAttribute("books", repo.findByOrderByCreateDate());
		Short idUser = getAuthUserId(repoU);
		List<Book> favoritesList = new ArrayList<Book>();
		List<Favorites> list = repoF.findAll();
		for (int i = 0; i < list.size(); i++) {
			if (idUser == list.get(i).getIdUser()) {
				favoritesList.add(repo.getOne(list.get(i).getIdBook()));
			}
		}
		model.addAttribute("favourites", favoritesList);
		model.addAttribute("contentPage", "favourites");
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