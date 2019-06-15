
package ua.step.bookshop.servises;

import ua.step.bookshop.models.User;
import java.util.Optional;

public interface UserService {
	void save(User user);

	Optional<User> findByLogin(String login);
}