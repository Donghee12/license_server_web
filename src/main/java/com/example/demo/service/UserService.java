package com.example.demo.service;


import com.example.demo.dataclass.LicenseInfo;
import com.example.demo.dataclass.MemberEntity;
import com.example.demo.dataclass.UserDTO;
import com.example.demo.dataclass.UserEntity;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;


    @Autowired  // 또는 생성자 주입 방식을 사용
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteMemberAndUserEntities(long memberId) {
        memberRepository.deleteById(memberId);
        userRepository.deleteById(memberId);
    }
    @Transactional
    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);

        if (userEntity != null) {
            // 연관된 부모 엔티티(MemberEntity)를 먼저 삭제
            MemberEntity memberEntity = userEntity.getMemberEntity();
            if (memberEntity != null) {
                memberRepository.delete(memberEntity);
            }

            // 그 후 자식 엔티티(UserEntity)를 삭제
            userRepository.delete(userEntity);
        }
    }

    @Transactional
    public void updateUser(Long userId, String newUsername, String newRole, String newStatus) {
        // 사용자 엔티티 조회
        UserEntity userEntity = userRepository.findById(userId).orElse(null);

        if (userEntity != null) {
            // 연관된 부모 엔티티(MemberEntity)를 수정하지 않음

            // 자식 엔티티(UserEntity)를 수정
            userEntity.setName(newUsername);  // 새 사용자 이름 값 설정 등 필요한 수정 작업 수행
            userEntity.setRole(newRole);          // 새 역할 값 설정 등 필요한 수정 작업 수행
            userEntity.setStatus(newStatus);      // 새 상태 값 설정 등 필요한 수정 작업 수행
            userRepository.save(userEntity);      // 자식 엔티티 저장
        }
    }

    private String getNameFromEmail(String email) {
        if (email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return null;
    }

    public UserDTO getUserById(Long userId) {
        // userRepository 가 null 인 경우 예외 처리
        if (userRepository == null) {
            throw new RuntimeException("userRepository is null");
        }

        try {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);

        if (userEntity != null) {
            UserDTO userDTO = new UserDTO();
            // UserEntity 에서 UserDTO 로 필요한 데이터 복사
            userDTO.setId(userEntity.getId());
            userDTO.setName(userEntity.getName());
            userDTO.setMemberEmail(userEntity.getMemberEmail());
            userDTO.setRole(userEntity.getRole());
            userDTO.setStatus(userEntity.getStatus());
            // 추가로 필요한 데이터 가 있다면 계속해 서 복사

            return userDTO;
        } else {
            return null; // 사용자가 없을 경우에는 null 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // 예외를 다시 던지도록 수정
        }
    }

}
