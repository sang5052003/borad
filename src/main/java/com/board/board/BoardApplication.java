package com.board.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

		List<String> reply = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			reply.add("reply" + i);
		}
		postRepository.save(new Post("title1", "ㅁ;ㅣ아럼;이ㅏ러", "test1", reply));
		postRepository.save(new Post("title2", "ㅁ;ㅣ아럼;이ㅏ러", "test2"));
		postRepository.save(new Post("title3", "ㅁ;ㅣ아럼;이ㅏ러", "test2"));
		postRepository.save(new Post("title4", "ㅁ;ㅣ아럼;이ㅏ러", "test1"));
		postRepository.save(new Post("title5", "ㅁ;ㅣ아럼;이ㅏ러", "test1"));

		postRepository.save(new Post("title6", "ㅁ;ㅣ아럼;이ㅏ러", "test1", reply));
		postRepository.save(new Post("title7", "ㅁ;ㅣ아럼;이ㅏ러", "test2"));
		postRepository.save(new Post("title8", "ㅁ;ㅣ아럼;이ㅏ러", "test2"));
		postRepository.save(new Post("title9", "ㅁ;ㅣ아럼;이ㅏ러", "test1"));
		postRepository.save(new Post("title10", "ㅁ;ㅣ아럼;이ㅏ러", "test1"));

		postRepository.save(new Post("title11", "ㅁ;ㅣ아럼;이ㅏ러", "test1", reply));

	}

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
}
