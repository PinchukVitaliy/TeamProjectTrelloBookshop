package ua.step.bookshop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.step.bookshop.models.User;

public interface UserRepository extends JpaRepository<User, Short> {
    Optional<User> findByLogin(String login);
}