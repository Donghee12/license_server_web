package com.example.demo.service;


import com.example.demo.dataclass.MemberDTO;
import com.example.demo.dataclass.MemberEntity;
import com.example.demo.dataclass.UserEntity;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final  UserRepository userRepository;


    @Transactional
    public void saveSubscriptionTime(String userEmail, LocalDateTime subscriptionTime) {
        try {
            MemberEntity memberEntity = memberRepository.findByMemberEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            memberEntity.setSubscriptionTime(subscriptionTime);
            memberRepository.save(memberEntity);

        } catch (Exception e) {
            e.printStackTrace(); // 또는 로그로 기록
        }
    }
    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        // 2. repositroy의 save 메소드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);


    }

    public boolean isEmailUnique(String memberEmail){

        return !memberRepository.existsByMemberEmail(memberEmail);
    }



    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 회원이 입력한 이메일로 DB에서 조회릏하
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        Optional<MemberEntity> byMemberEmail =
                memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // 비밀번호 일치 하는 경우
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }else {
                // 비밀번호가 불일치 하는 경우
                return null;
            }
        }else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity =memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()){
            return null;
        }else{
            return "ok";
        }
    }


    public String generateAndSaveRandomValueWithExpiration(String userEmail, int months) {

        String randomValue = generateRandomMixedValue();

        LocalDateTime expirationTime = LocalDateTime.now().plus(months,ChronoUnit.MONTHS);

        saveRandomValueWithExpiration(userEmail,randomValue,expirationTime,months);
        updateRandomValue(userEmail,randomValue);

        return randomValue;
    }


    public String generateAndSaveRandomValue(String userEmail){
        return generateAndSaveRandomValueWithExpiration(userEmail,3);

    }

    private void saveRandomValueWithExpiration(String userEmail, String randomValue, LocalDateTime expirationTime,int months) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Member no found"));

        memberEntity.setRandomMixedValue(randomValue);
        memberEntity.setSubscriptionExpirationTime(expirationTime);
        memberEntity.setSubscriptionMonths(months); // subscriptionMonths 저장 추가

        if (months == 3) {
            memberEntity.setProductName("ProductB");
        } else if (months == 1) {
            memberEntity.setProductName("ProductA");
        }

        memberRepository.save(memberEntity);

    }
    public List<MemberEntity> findByProductName(String productName) {
        return memberRepository.findByProductName(productName);
    }
    public void updateAndCalculateUserCount() {
        List<MemberEntity> productAMembers = findByProductName("ProductA");
        List<MemberEntity> productBMembers = findByProductName("ProductB");

        // 각각의 User_count 계산
        int productAUserCount = productAMembers.size();
        int productBUserCount = productBMembers.size();

        // 각 MemberEntity에 User_count 설정
        productAMembers.forEach(memberEntity -> memberEntity.setUserCount(productAUserCount));
        productBMembers.forEach(memberEntity -> memberEntity.setUserCount(productBUserCount));

        // 저장
        memberRepository.saveAll(productAMembers);
        memberRepository.saveAll(productBMembers);
    }
    @Transactional
    public void updateRandomValue(String userEmail, String randomValue) {

        Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(userEmail);

        optionalMember.ifPresent(member ->{
            member.setRandomMixedValue(randomValue);
            memberRepository.save(member);

            // 랜덤 값이 있으면 status를 active로 업데이트
            if (randomValue != null) {
                updateUserStatus(member.getUser());
            }else {
                System.out.println("RandomValue is null. updateUserStatus not called.");
            }

        });
        // 추가로 로그를 통해 트랜잭션이 제대로 동작하는지 확인
        System.out.println("Transaction committed successfully");
    }
    private void updateUserStatus(UserEntity userEntity) {
        if (userEntity != null) {
            userEntity.updateStatus();
            userRepository.save(userEntity);
            System.out.println("User status updated successfully");
        }
    }
    private String generateRandomMixedValue() {
        byte[] randomBytes = new byte[16];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }


    @Transactional
    public void subscribe(String userEmail) {
        String randomValue = generateRandomMixedValue();
        updateRandomValue(userEmail, randomValue);

        // 구독 후 상태 업데이트
        updateStatus(userEmail);
    }


    private void updateStatus(String userEmail) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(userEmail);

        optionalMember.ifPresent(member -> {
            // 멤버 엔티티에서 유저 엔티티 참조 가져오기
            UserEntity userEntity = member.getUser();

            if (userEntity != null) {
                userEntity.updateStatus();
                userRepository.save(userEntity);
            }
        });
    }



}
