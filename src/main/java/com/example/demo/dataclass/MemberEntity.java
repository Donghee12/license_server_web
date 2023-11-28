package com.example.demo.dataclass;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class MemberEntity {
    @Id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto
    private Long id;

    @Column(unique = true) // unique 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    private LocalDateTime subscriptionTime;
    @Column
    private String randomMixedValue;

    @Column(nullable = false)
    private int subscriptionMonths;

    @Column
    private LocalDateTime subscriptionExpirationTime;

    @OneToOne(mappedBy = "memberEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEntity user;

    @Column(name = "product_name")
    private String productName;

    @Transient // 데이터베이스에 저장하지 않는 필드
    private int userCount;

    private String Status;
    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setSubscriptionMonths(memberDTO.getSubscriptionMonths());
        // UserEntity 에도 저장
        UserEntity userEntity = UserEntity.fromMemberEntity(memberEntity);
        memberEntity.setUser(userEntity);

        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());

        return memberEntity;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public UserEntity getUserEntity() {
        return user;
    }



    public Date getSubscriptionExpirationDate() {
        if (subscriptionExpirationTime != null) {
            // LocalDateTime을 Date로 변환
            return java.sql.Timestamp.valueOf(subscriptionExpirationTime);
        } else {
            return null;
        }
    }

}
