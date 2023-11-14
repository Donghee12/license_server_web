package com.example.demo.service;

import com.example.demo.dataclass.LicenseInfo;
import com.example.demo.repository.LicenseInfoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class
LicenseService {

    @Autowired
    private LicenseInfoRepository licenseInfoRepository; // LicenseInfoRepository 주입

    @Transactional
    public void deleteLicense(String licenseId) {
        try {
            // 라이선스 삭제 로직을 수행
            Optional<LicenseInfo> optionalLicense = licenseInfoRepository.findById(licenseId);

            if (optionalLicense.isPresent()) {
                LicenseInfo licenseInfo = optionalLicense.get();
                licenseInfoRepository.delete(licenseInfo);
            }
        } catch (Exception e) {
            // 로그 등을 활용하여 예외 처리
            Logger logger = LoggerFactory.getLogger(LicenseService.class);
            logger.error("라이선스 삭제 중 오류 발생", e);
        }
    }
    @Transactional
    public List<LicenseInfo> searchLicense(String licenseKey) {
        try {
            // 라이선스 검색 로직을 수행
            return licenseInfoRepository.findByLicenseKeyContaining(licenseKey);
        } catch (Exception e) {
            // 로그 등을 활용하여 예외 처리
            Logger logger = LoggerFactory.getLogger(LicenseService.class);
            logger.error("라이선스 검색 중 오류 발생", e);
            return Collections.emptyList();
        }
    }

}
