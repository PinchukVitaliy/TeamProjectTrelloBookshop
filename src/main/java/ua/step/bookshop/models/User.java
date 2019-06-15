package ua.step.bookshop.models;

import javax.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	protected Short id;
	private String login;
	private String email;
	private String password;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_has_roles", joinColumns = {
			@JoinColumn(name = "users_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "roles_id", nullable = false, updatable = false) })
	private List<Role> roles = new ArrayList<Role>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Book> bookList;

	@ManyToMany
	private List<Book> books;

	public User() {
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String name) {
		this.login = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public List<Book> bookRemove(List<Book> books, Book book) {
		books.remove(book);
		return books;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
