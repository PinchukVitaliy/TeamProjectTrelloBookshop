package ua.step.bookshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.step.bookshop.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	List<Book> findByOrderByCreateDate();
}