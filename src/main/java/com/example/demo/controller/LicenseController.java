package com.example.demo.controller;

import com.example.demo.repository.LicenseInfoRepository;
import com.example.demo.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/licenses")
public class LicenseController {

    private final LicenseService licenseService;
    @Autowired
    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }
    @DeleteMapping("/{licenseKey}")
    @Transactional
    public ResponseEntity<String> deleteLicense(@PathVariable String licenseKey) {
        try {
            // 삭제 로직
            licenseService.deleteLicense(licenseKey);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting license");
        }
    }
}
