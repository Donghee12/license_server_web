package com.example.demo.dataclass;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String name;

    @Column
    private String role;

    @Column
    private String status;

    public static UserEntity fromMemberEntity(MemberEntity memberEntity) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(memberEntity.getId());
            userEntity.setMemberEmail(memberEntity.getMemberEmail());
            userEntity.setName(getNameFromEmail(memberEntity.getMemberEmail()));
            userEntity.setRole("user");
            userEntity.setStatus(memberEntity.getRandomMixedValue() != null ? "active" : "inactive");
            return userEntity;
        } catch (Exception e) {
            System.out.println("Failed to convert MemberEntity to UserEntity: " + e.getMessage());
            return null;
        }
    }

    private static String getNameFromEmail(String email) {
        if (email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return null;
    }
}
