package com.simplilearn.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.simplilearn.application.model.Book;

@Repository
public class BookDAO implements BookRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate.queryForObject("select count(*) from books", Integer.class);
	}

	@Override
	public int save(Book book) {
		return jdbcTemplate.update("insert into books (id, name, price) values(?,?,?)", book.getId(), book.getName(),
				book.getPrice());
	}

	@Override
	public int update(Book book) {
		return jdbcTemplate.update("update books set price = ? where id = ?", book.getPrice(), book.getId());
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update("delete from books where id = ?", id);
	}

	@Override
	public List<Book> findAll() {
		return jdbcTemplate.query("select * from books",
				(rs, rowNum) -> new Book(rs.getLong("id"), rs.getString("name"), rs.getBigDecimal("price")));
	}

	@SuppressWarnings("deprecation")
	@Override
	public Optional<Book> findById(Long id) {
		return jdbcTemplate.queryForObject("select * from books where id = ?", new Object[] { id }, (rs,
				rowNum) -> Optional.of(new Book(rs.getLong("id"), rs.getString("name"), rs.getBigDecimal("price"))));
	}
}