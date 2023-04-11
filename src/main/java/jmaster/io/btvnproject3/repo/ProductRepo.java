package jmaster.io.btvnproject3.repo;

import jmaster.io.btvnproject3.entity.Product;
import jmaster.io.btvnproject3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM product ORDER BY id DESC LIMIT 1",
            nativeQuery = true)
    Product get();

}
