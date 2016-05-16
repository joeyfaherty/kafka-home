package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by joey on 5/16/16.
 */
public class BasicUser {

    @JsonProperty(value="name")
    private String name;
    @JsonProperty(value="age")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
