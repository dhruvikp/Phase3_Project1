package com.simplilearn.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.simplilearn.application.model.Book;
import com.simplilearn.application.services.BookDAO;

@SpringBootApplication
public class BookCrudCommandLineApplication implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(BookCrudCommandLineApplication.class);

	@Autowired
	BookDAO bookDAO;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		log.info("Starting application");
		SpringApplication.run(BookCrudCommandLineApplication.class, args);
		log.info("Application Finished");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("EXECUTING : command line runner");

		for (int i = 0; i < args.length; ++i) {
			log.info("args[{}]: {}", i, args[i]);
		}

		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Book book1 = (Book) appContext.getBean("book1");
		Book book2 = (Book) appContext.getBean("book2");
		Book book3 = (Book) appContext.getBean("book3");
		System.out.println(book1);
		System.out.println(book2);
		System.out.println(book3);

		
		List<Book> books = new ArrayList<>();
		books.add(book1);
		books.add(book2);
		books.add(book3);

		jdbcTemplate.execute("DROP TABLE IF EXISTS books");
		jdbcTemplate.execute("CREATE TABLE books(" + "id int(11) not null, name VARCHAR(255), price NUMERIC(15, 2))");

		log.info("[SAVE]");
		books.forEach(book -> {
			log.info("Saving...{}", book.getName());
			bookDAO.save(book);
		});

		// count
		log.info("[COUNT] Total books: {}", bookDAO.count());

		// find all
		log.info("[FIND_ALL] {}", bookDAO.findAll());

		// find by id
		log.info("[FIND_BY_ID] :2L");
		Book book = bookDAO.findById(2L).orElseThrow(IllegalArgumentException::new);
		log.info("{}", book);

		// update
		log.info("[UPDATE] :2L :99.99");
		book.setPrice(new BigDecimal("99.99"));
		log.info("rows affected: {}", bookDAO.update(book));

		// delete
		log.info("[DELETE] :3L");
		log.info("rows affected: {}", bookDAO.deleteById(3L));

		// find all
		log.info("[FIND_ALL] {}", bookDAO.findAll());
	}
}
