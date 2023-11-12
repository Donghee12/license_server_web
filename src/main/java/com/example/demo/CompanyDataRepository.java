package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDataRepository extends JpaRepository<CompanyData, Long> {
    // 필요한 쿼리 메서드를 여기에 추가할 수 있습니다.
}
