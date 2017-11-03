package com.board.board;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

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

    @RequestMapping(value = "/post/put", method = RequestMethod.PUT)
    public Post putPostById(@RequestBody String jsonData) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});

        String title = map.get("title").toString();
        String contents = map.get("contents").toString();
        String id = map.get("id").toString();


        //find data for update
        Long idL = Long.parseLong(id);
        Post post = postRepository.findOne(idL);

        //update data
        post.title = title;
        post.contents = contents;

        //
//        List<String> setReply = new ArrayList<>();
//        List<Object> replyList = (List<Object>) map.get("reply");
//        for(Object obj : replyList){
//            LinkedHashMap<Object, Object> replyMap = (LinkedHashMap<Object, Object>) obj;
//            String reply = replyMap.get("v").toString();
//            setReply.add(reply);
//        }

        List<String> setReply = new ArrayList<>();
        List<Object> replyList = (List<Object>) map.get("reply");
        for(Object obj : replyList){
            String reply = obj.toString();
            setReply.add(reply);
        }

        post.setReply(setReply);

        postRepository.save(post); //same id exist, but no need to delete(auto upgraded..maybe)

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

    //
    @RequestMapping(value = "/post/write/reply", method = RequestMethod.POST)
    public void postReply(@RequestBody String jsonData) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});

        String id = map.get("id").toString();

        //find data for update
        Long idL = Long.parseLong(id);
        Post post = postRepository.findOne(idL);

        //작성한 댓글만.. 하도록 수정
        String reply = map.get("reply").toString();
        post.getReply().add(reply);

        postRepository.save(post);
    }

    @RequestMapping(value = "/post/reply/delete", method = RequestMethod.DELETE)
    public void deleteReply(@RequestBody String jsonData) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = new HashMap<>();
        map = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});

        String id = map.get("id").toString();

        //find data for update
        Long idL = Long.parseLong(id);
        Post post = postRepository.findOne(idL);

        //작성한 댓글만.. 하도록 수정
        String replyIdx = map.get("replyIdx").toString();
        List<String> list = (List<String>) post.getReply();
        list.remove(Integer.parseInt(replyIdx));

        postRepository.save(post);
    }

//    @RequestMapping(value = "/post/remove", method = RequestMethod.DELETE)
//    public void postDelete(@RequestBody PostWrapper postIds){
//
//        for(String id : postIds.postIdList){
//            postRepository.delete(Long.parseLong(id));
//        }
//    }
}
