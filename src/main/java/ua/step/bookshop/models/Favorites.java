package ua.step.bookshop.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Favorites")
@Data
public class Favorites {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer idBook;
	private Short idUser;

	public Favorites() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdBook() {
		return idBook;
	}

	public void setIdBook(Integer idBook) {
		this.idBook = idBook;
	}

	public Short getIdUser() {
		return idUser;
	}

	public void setIdUser(Short idUser) {
		this.idUser = idUser;
	}
}
