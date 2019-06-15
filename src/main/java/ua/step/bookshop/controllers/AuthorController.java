package ua.step.bookshop.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.step.bookshop.models.Author;
import ua.step.bookshop.repositories.AuthorRepository;

@Controller
public class AuthorController {
	@Autowired
	private AuthorRepository authorRepo;

	@GetMapping("/authors")
	private String getAuthors(Model model) {
		model.addAttribute("authors", authorRepo.findAll());
		model.addAttribute("contentPage", "authors");
		return "index";
	}

	@GetMapping("/authors/add")
	private String getAddAuthor(@ModelAttribute Author author, Model model) {
		model.addAttribute("contentPage", "addAuthor");
		return "index";
	}

	@PostMapping("/authors/add")
	private String addAuthor(@ModelAttribute Author author) {
		boolean isEmty = true;
		List<Author> authors = authorRepo.findAll();
		for (int i = 0; i < authors.size(); i++) {
			if (authors.get(i).getName().equals(author.getName())) {
				isEmty = false;
			}
		}
		if (isEmty) {
			authorRepo.saveAndFlush(author);
			return "redirect:/authors";
		} else {
			return "redirect:/authors/add";
		}
	}

	@GetMapping(value = "/authors", params = { "search" })
	private String getSearchAuthor(Model model, String search) {
		int count = 0;
		List<Author> authorList = authorRepo.findAll();
		List<Author> searchList = null;
		if (!search.isEmpty() && count == 0) {
			searchList = new ArrayList<Author>();
			for (int i = 0; i < authorList.size(); i++) {
				if (authorList.get(i).getName().regionMatches(true, 0, search, 0, search.length())) {
					searchList.add(authorList.get(i));
					count++;
				}
			}
			model.addAttribute("authors", searchList);
			model.addAttribute("contentPage", "authors");
		}
		if (search.isEmpty() || count == 0) {
			model.addAttribute("authors", searchList);
			model.addAttribute("contentPage", "/fragments/searchResultNull");
		}
		return "index";
	}
}