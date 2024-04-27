package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query(value = "select * " +
                    "from users " +
                    "where id = :id and status = 'A'", nativeQuery = true)
    Optional<UserEntity> findById(@Param("id") int id);

    @Query(value = "select * " +
            "from users " +
            "where status = 'A'", nativeQuery = true)
    Page<UserEntity> findAll(Pageable pageable);
}
