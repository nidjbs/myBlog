package com.hyl.blog.dao;
import java.util.List;

import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.Label;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LabelRepository extends JpaRepository<Label,Integer> {
    Label findByName(String name);

    @Query(nativeQuery = true ,value = "SELECT l.* FROM `t_blog` b,`t_label` l,`t_blogs_labels` bl " +
            "WHERE bl.`blog_id`= b.id AND bl.`label_id`=l.`id` AND b.status=1 " +
            "GROUP BY l.`id` ORDER BY COUNT(b.`id`) DESC LIMIT 0,:size")
    List<Label> findTop(@Param("size") Integer size);

    @Query(nativeQuery = true,value = "SELECT l.id,l.name FROM `t_blog` b,`t_blogs_labels` bl,`t_label` l \n" +
            " WHERE bl.`blog_id`=b.`id` AND bl.`label_id`=l.`id` AND b.`status`=1 \n" +
            " GROUP BY l.`id`\n" +
            " ORDER BY COUNT(b.`id`) DESC")
    List<Label> findAllAndBlogPublish();

    @Query(nativeQuery = true,value = "SELECT  COUNT(b.`id`)FROM `t_blogs_labels` bl,`t_blog` b WHERE bl.`blog_id` = b.`id` AND b.`status`=1 AND bl.`label_id`=:id")
    Integer countPublishBlogByLabelId(@Param("id") Integer id);

}
