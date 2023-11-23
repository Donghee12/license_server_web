package com.example.demo.dataclass;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientInfoDTO {
    private String email;
    private String hardwareId;
    private String licenseKey;
    private String userName;
    private String status;
    private LocalDateTime dateRegistered;
    private LocalDateTime expiryDate;

    // 생성자, 게터, 세터 등 필요한 메서드 구현
}

