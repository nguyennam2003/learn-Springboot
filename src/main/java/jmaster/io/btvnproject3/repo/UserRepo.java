package jmaster.io.btvnproject3.repo;

import jmaster.io.btvnproject3.DTO.PageDTO;
import jmaster.io.btvnproject3.DTO.UserDTO;
import jmaster.io.btvnproject3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer>, Repository<User, Integer> {

    Page<User> findByNameLike(String name, Pageable pageable);

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.birthdate = :x ")
    List<User> searchByBirthdate(@Param("x") Date s);

    @Query("SELECT u FROM User u WHERE u.name LIKE :x ")
    Page<User> searchByName(@Param("x") String s, Pageable pageable);


}
