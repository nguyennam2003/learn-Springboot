package jmaster.io.btvnproject3.repo;

import jmaster.io.btvnproject3.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepo extends JpaRepository<Category, Integer>, Repository<Category, Integer> {

    @Query("select c from Category c where c.name like :x")
    Page<Category> findByNameLike(@Param("x") String x, Pageable pageable);

    Page<Category> searchByName(String name, Pageable pageable);
}
