package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicenseController {

    @Autowired
    private LicenseService licenseService; // LicenseService 주입

    @PostMapping("/delete-license")
    public ResponseEntity<String> deleteLicense(@RequestParam("licenseId") String licenseId) {
        try {
            // 라이선스 삭제 로직을 수행
            boolean success = licenseService.deleteLicense(licenseId);

            if (success) {
                return ResponseEntity.ok("삭제 성공");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류 발생");
        }

    }
}
