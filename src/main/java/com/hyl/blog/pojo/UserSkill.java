package com.hyl.blog.pojo;

import javax.persistence.*;

//用户技能
@Entity
@Table(name = "t_userSkill")
public class UserSkill {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @ManyToOne
    private User userSkill;

    public UserSkill() {
    }

    @Override
    public String toString() {
        return "UserTag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userSkill=" + userSkill +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUserSkill() {
        return userSkill;
    }

    public void setUserSkill(User userTag) {
        this.userSkill = userTag;
    }

    public UserSkill(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
