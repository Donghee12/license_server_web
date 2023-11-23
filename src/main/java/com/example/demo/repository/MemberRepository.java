package com.example.demo.repository;

import com.example.demo.dataclass.MemberEntity;
import com.example.demo.dataclass.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    // 이멜이로 회원 정보 조회
    Optional<MemberEntity> findByMemberEmail(String memberEmail);


    boolean existsByMemberEmail(String memberEmail);

}
