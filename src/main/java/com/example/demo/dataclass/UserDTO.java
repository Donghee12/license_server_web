package com.example.demo.dataclass;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String name;
    private String memberEmail;
    private String role;
    private String status;

    // 생성자, 게터, 세터 등 필요한 메서드 추가
}
