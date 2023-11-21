package com.example.demo.repository;


import com.example.demo.dataclass.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


    void deleteById(long id);
    List<UserEntity> findById(long id);

}
