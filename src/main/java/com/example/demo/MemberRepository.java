package com.example.demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 추가적인 메서드 정의 가능
}
