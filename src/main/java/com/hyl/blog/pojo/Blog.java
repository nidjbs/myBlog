package com.hyl.blog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="t_blog")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Blog implements Serializable {
    @Id
    @GeneratedValue //主键策略
    @Range(min = 0,max = Integer.MAX_VALUE,message = "id is too big or illegal!")
    private Integer id;
    @NotBlank(message = "Title does not empty!")
    @Length(max = 255,min = 0,message = "Title is too long or illegal!")
    private String title;
    //首图
    @NotBlank(message = "PictureUrl does not empty!")
    @Length(max = 1000,min = 0,message = "PictureUrl is too long or illegal!")
    private String firstPictureUrl;
    //标记
    @NotBlank(message = "Tag  does not selected!")
    private String tag;
    //浏览次数
    @Range(min = 0,max = 999999,message = "Illegal operation!")
    private Integer viewedCount;
    //开启赞赏功能
    private boolean openAppreciate;
    //版权限制开启
    private boolean openCopyright;
    //评论功能开启
    private boolean openComment;
    //发布状态 true 代表以发布 false代表未发布
    private boolean status;
    //是否在推荐状态
    private boolean recommend;

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    //创建时间
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    //上一次更新时间
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    //用户关联
    @ManyToOne
    private User user;
    //评论关联
    @OneToMany(mappedBy = "blog")
    private List<Comment> comment;
    //标签关联
    @ManyToMany(targetEntity = Label.class,cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinTable(name = "t_blogs_labels",
            joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "label_id", referencedColumnName = "id"))
    private List<Label> labelList = new ArrayList<>();
    //分类关联
    @ManyToOne()
    private Type type;
    //不添加到数据库的字段，方便用于添加指定的label
    @Transient
    private String labelIds;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //博客描述
    @NotBlank(message = "description does not empty!")
    private String description;

    public String getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(String labelIds) {
        this.labelIds = labelIds;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<Label> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Label> labelList) {
        this.labelList = labelList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstPictureUrl='" + firstPictureUrl + '\'' +
                ", tag='" + tag + '\'' +
                ", viewedCount=" + viewedCount +
                ", openAppreciate=" + openAppreciate +
                ", openCopyright=" + openCopyright +
                ", openComment=" + openComment +
                ", status=" + status +
                ", recommend=" + recommend +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                ", user=" + user +
                ", comment=" + comment +
                ", labelList=" + labelList +
                ", type=" + type +
                ", labelIds='" + labelIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPictureUrl() {
        return firstPictureUrl;
    }

    public void setFirstPictureUrl(String firstPictureUrl) {
        this.firstPictureUrl = firstPictureUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getViewedCount() {
        return viewedCount;
    }

    public void setViewedCount(Integer viewedCount) {
        this.viewedCount = viewedCount;
    }

    public boolean isOpenAppreciate() {
        return openAppreciate;
    }

    public void setOpenAppreciate(boolean openAppreciate) {
        this.openAppreciate = openAppreciate;
    }

    public boolean isOpenCopyright() {
        return openCopyright;
    }

    public void setOpenCopyright(boolean openCopyright) {
        this.openCopyright = openCopyright;
    }

    public boolean isOpenComment() {
        return openComment;
    }

    public void setOpenComment(boolean openComment) {
        this.openComment = openComment;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Blog() {
    }

    public void init(){
        this.labelIds = this.labelToIds(this.getLabelList());
    }
    //由label数组转为1,2,3这种形式的字符串
    private String labelToIds(List<Label> labels){
        if(!labels.isEmpty()){
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for(Label label : labels){
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(label.getId());
            }
            return ids.toString();
        }else{
            return labelIds;
        }
    }
}
