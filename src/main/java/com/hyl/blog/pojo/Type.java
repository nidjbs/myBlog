package com.hyl.blog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "t_type")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Type implements Serializable {
    @Id
    @GeneratedValue
    @Range(min = 0,max = Integer.MAX_VALUE,message = "id is too big or illegal!")
    private Integer id;
    @NotBlank(message = "label name does not empty!")
    private  String name;
    @OneToMany(mappedBy = "type")
    private List<Blog> blogList = new ArrayList<>();
    //该类型已发布的博客数
    @Transient
    private Integer countpb;
    @Transient
    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCountpb() {
        return countpb;
    }

    public void setCountpb(Integer countpb) {
        this.countpb = countpb;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogList=" + blogList +
                ", countpb=" + countpb +
                '}';
    }

    public Type() {
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
}
