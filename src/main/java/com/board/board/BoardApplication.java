package com.board.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication implements CommandLineRunner {

	@Autowired
	public PersonRepository repository;

	@Autowired
	public PostRepository postRepository;

	@Override
	public void run(String... strings) throws Exception {

		// 초기 데이터
		// save a couple of customers
		repository.save(new Person("Jack", "Bauer", "test1"));
		repository.save(new Person("Chloe", "O'Brian", "test2"));
		repository.save(new Person("Kim", "Bauer", "test3"));
		repository.save(new Person("David", "Palmer", "test4"));
		repository.save(new Person("Michelle", "Dessler", "test5"));

		postRepository.save(new Post("title1", "ㅁ;ㅣ아럼;이ㅏ러", "test1"));
		postRepository.save(new Post("title2", "ㅁ;ㅣ아럼;이ㅏ러", "test2"));
		postRepository.save(new Post("title3", "ㅁ;ㅣ아럼;이ㅏ러", "test2"));
		postRepository.save(new Post("title4", "ㅁ;ㅣ아럼;이ㅏ러", "test1"));
		postRepository.save(new Post("title5", "ㅁ;ㅣ아럼;이ㅏ러", "test1"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
}
