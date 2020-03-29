package com.hyl.blog.pojo;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String qqNumber;
    private String weixinUrl;
    private String githubUrl;
    private String headPointUrl;
    private String profile;//简介

    @OneToMany(mappedBy = "user")
    private List<Blog> blogList = new ArrayList<>();

    @OneToMany(mappedBy = "userTag")
    private List<UserTag> tagList = new ArrayList<>();
    @OneToMany(mappedBy = "userSkill")
    private List<UserSkill> skillList = new ArrayList<>();

    public List<UserTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<UserTag> tagList) {
        this.tagList = tagList;
    }

    public List<UserSkill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<UserSkill> skillList) {
        this.skillList = skillList;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", qqNumber='" + qqNumber + '\'' +
                ", weixinUrl='" + weixinUrl + '\'' +
                ", githubUrl='" + githubUrl + '\'' +
                ", headPointUrl='" + headPointUrl + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getWeixinUrl() {
        return weixinUrl;
    }

    public void setWeixinUrl(String weixinUrl) {
        this.weixinUrl = weixinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getHeadPointUrl() {
        return headPointUrl;
    }

    public void setHeadPointUrl(String headPointUrl) {
        this.headPointUrl = headPointUrl;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public User() {
    }

    public User(Integer id, String username, String password, String email, String qqNumber, String weixinUrl, String githubUrl, String headPointUrl, String profile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.qqNumber = qqNumber;
        this.weixinUrl = weixinUrl;
        this.githubUrl = githubUrl;
        this.headPointUrl = headPointUrl;
        this.profile = profile;
    }
}
