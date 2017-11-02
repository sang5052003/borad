package com.board.board;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Post() { }

    public Post(String title, String contents, String authorId) {
        this.title = title;
        this.contents = contents;
        this.authorId = authorId;
    }

    public Post(String title, String contents, String authorId, List<String> reply) {
        this.title = title;
        this.contents = contents;
        this.authorId = authorId;
        this.reply = reply;
    }

    public String title;
    public String contents;
    public String authorId;

    @ElementCollection
    public Collection<String> reply;

    public Collection<String> getReply() {
        return reply;
    }

    public void setReply(List<String> reply) {
        this.reply = reply;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
