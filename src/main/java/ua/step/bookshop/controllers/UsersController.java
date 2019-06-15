package ua.step.bookshop.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.step.bookshop.models.User;
import ua.step.bookshop.repositories.UserRepository;

@Controller
public class UsersController {
	@Autowired
	private UserRepository userRepo;

	@GetMapping(value = "/admin/usersList")
	public String getUsersList(Model model) {
		model.addAttribute("users", userRepo.findAll());
		model.addAttribute("userId", getAuthUserId(userRepo));
		model.addAttribute("contentPage", "userList");
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