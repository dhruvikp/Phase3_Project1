package com.simplilearn.application.services;

import java.util.List;
import java.util.Optional;

import com.simplilearn.application.model.Book;

public interface BookRepository {
	int count();

	int save(Book book);

	int update(Book book);

	int deleteById(Long id);

	List<Book> findAll();
	
	Optional<Book> findById(Long id);
}
