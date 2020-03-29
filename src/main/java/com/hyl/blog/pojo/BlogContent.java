package com.hyl.blog.pojo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="t_blog_content")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"}) //jison数据可以有空
public class BlogContent implements Serializable {
    @Id
    @GeneratedValue //主键策略
    @Range(min = 0,max = Integer.MAX_VALUE,message = "id is too big or illegal!")
    private Integer contentId;

    @NotBlank(message = "Content does not empty!")
    private String content;

    @OneToOne(cascade = {CascadeType.ALL,CascadeType.REMOVE})
    @JoinColumn(name = "blog_id",referencedColumnName = "id")
    private Blog blog;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "BlogContent{" +
                "contentId=" + contentId +
                ", content='" + content + '\'' +
                ", blog=" + blog +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogContent() {
    }
}
