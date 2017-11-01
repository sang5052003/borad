package com.board.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostService {

    @Autowired
    PostRepository postRepository;

    @RequestMapping(value = "/post/title/{title}", method = RequestMethod.GET)
    public List<Post> getPost(@PathVariable("title") String title) {

        return postRepository.findByTitle(title);
    }
}
