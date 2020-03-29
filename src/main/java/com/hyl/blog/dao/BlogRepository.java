package com.hyl.blog.dao;

import java.util.List;

import com.hyl.blog.pojo.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends JpaRepository<Blog,Integer>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true and b.status = true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.status=true and b.title like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Query("select b from Blog b where b.status = true ")
    Page<Blog> findAllPublish(Pageable pageable);

    @Query(nativeQuery = true,value = "SELECT b.* FROM `t_blog` b,`t_blogs_labels` bl WHERE bl.`blog_id` = b.`id` AND bl.`label_id` = :id AND b.status = 1")
    Page<Blog> findBlogListByLabelId(@Param("id") Integer id, Pageable pageable);

    @Query("select function('date_format',b.creationTime,'%Y') as year from Blog b group by function('date_format',b.creationTime,'%Y') order by year DESC ")
    List<String> findCroupYear();

    @Query("select b from Blog b where function('date_format',b.creationTime,'%Y') = ?1 order by b.creationTime DESC ")
    List<Blog> findByYear(String query);

    @Query("select b from Blog b where b.status = 1 and b.type.id = ?1")
    Page<Blog> findBlogListByTypeId(Integer id,Pageable pageable);

}
