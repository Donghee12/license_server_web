package com.example.demo.dataclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequest {
    private String username;
    private String role;
    private String status;

    // 생성자, 게터, 세터 등 필요한 메서드 추가
}