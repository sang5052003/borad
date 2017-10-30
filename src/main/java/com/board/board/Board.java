package com.board.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;

    public Board() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
