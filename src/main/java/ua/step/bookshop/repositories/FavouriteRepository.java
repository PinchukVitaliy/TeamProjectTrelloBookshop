package ua.step.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.step.bookshop.models.Favorites;

public interface FavouriteRepository extends JpaRepository<Favorites, Integer> {
}