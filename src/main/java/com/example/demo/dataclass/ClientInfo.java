// ClientInfo.java
package com.example.demo.dataclass;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "client_info")
public class ClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private LocalDateTime subscriptionTime;

    @Column
    private String randomMixedValue;

    @Column(nullable = false)
    private int subscriptionMonths;

    @Column
    private LocalDateTime subscriptionExpirationTime;

    @Column
    private String name;

    @Column
    private String status;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name ="member_id", unique = true)
    private MemberEntity memberEntity;
    // 생성자, getter, setter 등 필요한 메서드 추가
    public static ClientInfo fromMemberAndUserEntities(MemberEntity memberEntity, UserEntity userEntity) {
        ClientInfo clientInfo = new ClientInfo();

        // MemberEntity에서 정보 가져오기
        clientInfo.setSubscriptionTime(memberEntity.getSubscriptionTime());
        clientInfo.setRandomMixedValue(memberEntity.getRandomMixedValue());
        clientInfo.setSubscriptionMonths(memberEntity.getSubscriptionMonths());
        clientInfo.setSubscriptionExpirationTime(memberEntity.getSubscriptionExpirationTime());

        // UserEntity에서 정보 가져오기
        clientInfo.setName(userEntity.getName());
        clientInfo.setStatus(userEntity.getStatus());
        clientInfo.setEmail(userEntity.getMemberEmail());
        // 기타 필요한 정보들 설정...

        return clientInfo;
    }
}
