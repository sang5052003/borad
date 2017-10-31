package com.board.board;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "posts", path = "post")
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    List<Post> findByTitle(@Param("title") String title);
}
