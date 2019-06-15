package ua.step.bookshop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ua.step.bookshop.models.Author;
import ua.step.bookshop.repositories.AuthorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AuthorRepository repository;

	@Test
	public void testAuthorSave() {
		Author author = new Author();
		author.setId(1);
		author.setName("abc");
		author.setBiography("qwerty");
		this.entityManager.persist(author);
		author = this.repository.getOne(1);
		assertThat(author.getName()).isEqualTo("abc");
		assertThat(author.getBiography()).isEqualTo("qwerty");
	}
}