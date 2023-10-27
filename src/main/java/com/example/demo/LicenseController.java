package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicenseController {

    @PostMapping("/delete-license")
    public ResponseEntity<String> deleteLicense(@RequestParam("licenseId") String licenseId) {
        try {
            // 여기에서 실제 삭제 작업을 수행

            // 삭제 작업이 성공한 경우
            return ResponseEntity.ok("삭제 성공");

            // 삭제 작업이 실패한 경우
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류 발생");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류 발생");
        }
    }
}
