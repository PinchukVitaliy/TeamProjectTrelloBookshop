package ua.step.bookshop.models;

import java.util.List;

public class BookDTO {
	private Integer id;
	private String name;
	private Integer year;
	private String price;
	private String background;
	private String rate;
	private User user;
	private Publisher publisher;
	private List<Genre> genres;
	private List<Author> authors;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = "" + rate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Genre> getGenreList() {
		return genres;
	}

	public void setGenreList(List<Genre> genreList) {
		this.genres = genreList;
	}

	public List<Author> getAuthorList() {
		return authors;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authors = authorList;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}