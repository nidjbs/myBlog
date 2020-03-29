package com.hyl.blog.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String email;
    private String headPointUrl;
    private String content;
    //指定与数据库对应的时间类型
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentTime;
    @ManyToOne
    private Blog blog;

    //建立自关联关系(父评论和子评论是一对多关系)
    @OneToMany(mappedBy ="parent" )
    private List<Comment> dependParent = new ArrayList<>();
    @ManyToOne
    private Comment parent ;



    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", headPointUrl='" + headPointUrl + '\'' +
                ", content='" + content + '\'' +
                ", commentTime=" + commentTime +
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPointUrl() {
        return headPointUrl;
    }

    public void setHeadPointUrl(String headPointUrl) {
        this.headPointUrl = headPointUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Comment() {
    }

    public Comment(String name, String email, String headPointUrl, String content, Date commentTime) {

        this.name = name;
        this.email = email;
        this.headPointUrl = headPointUrl;
        this.content = content;
        this.commentTime = commentTime;
    }
}
