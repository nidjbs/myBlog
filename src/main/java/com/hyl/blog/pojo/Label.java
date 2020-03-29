package com.hyl.blog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "t_label")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Label implements Serializable {
    @Id
    @GeneratedValue
    @Range(min = 0,max = Integer.MAX_VALUE,message = "id is too big or illegal!")
    private Integer id;
    @NotBlank(message = "label name does not empty!")
    private String name;

    @ManyToMany(mappedBy = "labelList",cascade = CascadeType.PERSIST)
    private List<Blog> blogList = new ArrayList<>();
    //该标签关联已发布的博客数
    @Transient
    private Integer countBlog;
    @Transient
    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCountBlog() {
        return countBlog;
    }

    public void setCountBlog(Integer countBlog) {
        this.countBlog = countBlog;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public Label(String name) {
        this.name = name;
    }

    public Label() {
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
}
