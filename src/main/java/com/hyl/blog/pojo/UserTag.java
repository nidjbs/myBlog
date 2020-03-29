package com.hyl.blog.pojo;

import javax.persistence.*;

//用户的个性标签
@Entity
@Table(name = "t_userTag")
public class UserTag {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ManyToOne
    private User userTag;

    public UserTag() {
    }

    @Override
    public String toString() {
        return "UserTag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userTag=" + userTag +
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

    public User getUserTag() {
        return userTag;
    }

    public void setUserTag(User userTag) {
        this.userTag = userTag;
    }

    public UserTag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
