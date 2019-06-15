package ua.step.bookshop.models;

import javax.persistence.*;

import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Genres")
@Data
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;
	private String name;

	@ManyToMany
	private List<Book> bookList;

	public Genre() {
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}