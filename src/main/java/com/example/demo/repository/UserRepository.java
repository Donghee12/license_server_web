package com.example.demo.repository;


import com.example.demo.dataclass.MemberEntity;
import com.example.demo.dataclass.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


    void deleteById(long id);
    List<UserEntity> findById(long id);

    Optional<UserEntity> findByMemberEmail(String memberEmail);
}
