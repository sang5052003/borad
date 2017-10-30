package com.board.board;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "board", path = "board")
public interface BoardRepository extends PagingAndSortingRepository<Board, Long>{

    List<Board> findByName(String name);
}
