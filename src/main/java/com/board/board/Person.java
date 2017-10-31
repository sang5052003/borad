package com.board.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

    public Person() {}

    public Person(String firstName, String lastName, String loginId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginId = loginId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String firstName;
    public String lastName;

    //
    public String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
