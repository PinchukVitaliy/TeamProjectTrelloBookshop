package ua.step.bookshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ua.step.bookshop.models.Book;
import ua.step.bookshop.models.Genre;
import ua.step.bookshop.repositories.BookRepository;

/**
 * 
 * @author Vitaly Sakaly
 *
 */

@Controller
public class GenreController {
  @Autowired
  private BookRepository repoB;

  @GetMapping("/sortByGenre/{id}")
  private String getGenres(@PathVariable("id") Short id, Model model) {
    List<Book> allBooks = repoB.findAll();
    List<Book> sortsdByGenres = new ArrayList<Book>();
    List<Genre> genresList;

    for (Book book : allBooks) {
      genresList = book.getGenres();
      for (Genre genre : genresList) {
        Short sortByGenreName = genre.getId();
        if (sortByGenreName.equals(id)) {
          sortsdByGenres.add(book);
        }
      }
    }
    model.addAttribute("books", sortsdByGenres);
    model.addAttribute("contentPage", "/fragments/books");
    return "sortByGenre";
    // return "index";

  /*@GetMapping("/genres")
  public String getGenres(Model model) {
    model.addAttribute("genres", repo.findAll());
    model.addAttribute("contentPage", "genres");
    return "index";*/

  }
}