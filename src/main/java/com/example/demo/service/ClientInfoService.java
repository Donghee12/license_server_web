package com.example.demo.service;


import com.example.demo.dataclass.ClientInfoDTO;
import com.example.demo.dataclass.MemberEntity;
import com.example.demo.dataclass.UserEntity;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClientInfoService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;



    public Optional<ClientInfoDTO> getClientInfoByEmail(String email) {
        // email을 기반으로 멤버 엔티티와 유저 엔티티 정보를 가져온다.

        Optional<UserEntity> userEntityOptional = userRepository.findByMemberEmail(email);
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByMemberEmail(email);

        if (userEntityOptional.isPresent() && memberEntityOptional.isPresent()) {
            // UserEntity와 MemberEntity 정보가 모두 있을 때만 ClientInfoDTO를 생성
            UserEntity userEntity = userEntityOptional.get();
            MemberEntity memberEntity = memberEntityOptional.get();

            // ClientInfoDTO에 필요한 정보를 담아서 반환
            ClientInfoDTO clientInfoDTO = new ClientInfoDTO();
            clientInfoDTO.setEmail(email);
            clientInfoDTO.setHardwareId(memberEntity.getMemberPassword());
            clientInfoDTO.setLicenseKey(memberEntity.getRandomMixedValue());
            clientInfoDTO.setUserName(userEntity.getName());
            clientInfoDTO.setStatus(userEntity.getStatus());

            // 나머지 필드도 동일하게 설정

            return Optional.of(clientInfoDTO);
        } else {
            // UserEntity 또는 MemberEntity 중 하나라도 없을 때는 빈 Optional을 반환
            return Optional.empty();
        }
    }



}
