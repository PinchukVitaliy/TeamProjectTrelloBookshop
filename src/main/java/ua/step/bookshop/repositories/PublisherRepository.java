package ua.step.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.step.bookshop.models.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Short> {
}