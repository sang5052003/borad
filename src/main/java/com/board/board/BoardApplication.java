package com.board.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication implements CommandLineRunner {

	@Autowired
	public PersonRepository repository;

	@Override
	public void run(String... strings) throws Exception {

		// 초기 데이터
		// save a couple of customers
		repository.save(new Person("Jack", "Bauer"));
		repository.save(new Person("Chloe", "O'Brian"));
		repository.save(new Person("Kim", "Bauer"));
		repository.save(new Person("David", "Palmer"));
		repository.save(new Person("Michelle", "Dessler"));

	}

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
}
