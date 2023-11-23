package com.example.demo.repository;

import com.example.demo.dataclass.ClientInfo;
import com.example.demo.dataclass.MemberEntity;
import com.example.demo.dataclass.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {
    Optional<ClientInfo> findByEmail(String email);

    Optional<ClientInfo> findByUserEntity(UserEntity userEntity);

    boolean existsByUserEntityMemberEmail(String memberEmail);
}