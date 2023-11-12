package com.example.demo;

import jakarta.persistence.*;

@Entity
public class CompanyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String licenseKey;
    private String productName;

    private int maxNoUsers;
    private int userCount;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMaxNoUsers() {
        return maxNoUsers;
    }

    public void setMaxNoUsers(int maxNoUsers) {
        this.maxNoUsers = maxNoUsers;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
