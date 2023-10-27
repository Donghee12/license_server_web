package com.example.demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseInfoRepository extends JpaRepository<LicenseInfo, String> {
    // 추가적인 데이터베이스 쿼리 메서드 정의

}

