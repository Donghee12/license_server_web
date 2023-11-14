package com.example.demo.repository;
import com.example.demo.dataclass.LicenseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LicenseInfoRepository extends JpaRepository<LicenseInfo, String> {
    // 추가적인 데이터베이스 쿼리 메서드 정의
    void deleteByLicenseKey(String licenseKey);

    List<LicenseInfo> findByLicenseKeyContaining(String licenseKey);
}

