package com.hyl.blog.dao;

import java.util.List;

import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.Type;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TypeRepository extends JpaRepository<Type,Integer> {
    Type findByName(String name);

    @Query(nativeQuery = true ,value = "SELECT t.id,t.name FROM `t_blog` b,`t_type` t WHERE b.`status`=1 AND b.`type_id` = t.`id` GROUP BY t.`id` ORDER BY COUNT(b.`id`) DESC LIMIT 0,:size")
    List<Type> findTop(@Param("size") Integer size);

    @Query(nativeQuery = true,value = "SELECT t.id,t.name FROM `t_blog` b,`t_type` t WHERE b.`status`=1 AND b.`type_id` = t.`id` \n" +
            "GROUP BY t.`id` ORDER BY COUNT(b.`id`) DESC")
    List<Type> findAllAndBlogPublish();

    @Query("select function('count',b.id) from Blog b,Type p where b.type.id=p.id and b.status=1 and p.id=?1 ")
    Integer countPublishBlogByTypeId(Integer id);
}
