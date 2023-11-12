package com.example.demo.dataclass;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class LicenseInfo {
    // getter, setter 및 다른 필수 메서드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String licenseKey;
    // Product Name의 getter 및 setter
    private String productName;
    // Days Validity의 getter 및 setter
    private int daysValidity;
    // Max No Users의 getter 및 setter
    private int maxNoUsers;
    // User Count의 getter 및 setter
    private int userCount;
    // Status의 getter 및 setter
    private String status;

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDaysValidity(int daysValidity) {
        this.daysValidity = daysValidity;
    }

    public void setMaxNoUsers(int maxNoUsers) {
        this.maxNoUsers = maxNoUsers;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
