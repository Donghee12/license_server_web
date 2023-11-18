package com.example.demo.service;


import com.example.demo.dataclass.MemberEntity;
import com.example.demo.dataclass.UserEntity;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void convertAndSaveUser(MemberEntity memberEntity) {
        // 변환 및 저장 로직
        UserEntity userEntity = convertToUserEntity(memberEntity);
        userRepository.save(userEntity);
    }

    private UserEntity convertToUserEntity(MemberEntity memberEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setMemberEmail(memberEntity.getMemberEmail());
        userEntity.setName(getNameFromEmail(memberEntity.getMemberEmail())); // 이름 추출 메서드 호출
        userEntity.setRole("user"); // 예시: 기본 역할은 "user"
        userEntity.setStatus(memberEntity.getRandomMixedValue() != null ? "active" : "inactive");

        // 나머지 필드들 설정...

        return userEntity;
    }

    private String getNameFromEmail(String email) {
        if (email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return null;
    }
}
