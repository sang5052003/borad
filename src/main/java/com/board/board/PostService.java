package com.board.board;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostService {

    @Autowired
    PostRepository postRepository;

//    @RequestMapping(value = "/post", method = RequestMethod.GET)
//    public List<Post> getPostList() {
//
//        return postRepository.findAllBy();
//    }
//    @RequestMapping(value = "/post", method = RequestMethod.GET)
//    public List<Post> getPosts(){
//        return (List<Post>) postRepository.findAll();
//    }

    @RequestMapping(value = "/post/title/{title}", method = RequestMethod.GET)
    public List<Post> getPostByTitle(@PathVariable("title") String title) {

        return postRepository.findByTitle(title);
    }

    @RequestMapping(value = "/post/id/{id}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable("id") String id) {

        Long idL = Long.parseLong(id);
        Post post = postRepository.findById(idL);

        return post;
    }

    @RequestMapping(value = "/post/write", method = RequestMethod.POST)
    public void postPost(@RequestBody String jsonData) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map = mapper.readValue(jsonData, new TypeReference<Map<String, String>>(){});

        String title = map.get("title");
        String contents = map.get("contents");
        String authorId = map.get("authorId");

        postRepository.save(new Post(title, contents, authorId));
    }
}
