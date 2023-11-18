package com.example.demo.controller;

import com.example.demo.dataclass.MemberDTO;
import com.example.demo.dataclass.MemberEntity;
import com.example.demo.dataclass.UserEntity;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입 자동적으로 만들어 준다
    private final MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("generateRandomMixedValue")
    public String generateRandomMixedValue(Model model, HttpSession session){
        String userEmail = (String) session.getAttribute("loginEmail");

        if (userEmail == null){



            return "main";
        }
        Optional<MemberEntity> existingMember = memberRepository.findByMemberEmail(userEmail);
        if (existingMember.isPresent() && existingMember.get().getRandomMixedValue() != null){
            String message = "이미 구독중 입니다";
            model.addAttribute("message",message);
            return "sub";
        }

        String randomValue = memberService.generateAndSaveRandomValue(userEmail);

        model.addAttribute("randomValue",randomValue);

        String message = "구독 완료";
        model.addAttribute("message",message);

        return "sub";
    }


    //회원가입 페이지 출력 요청
    @GetMapping("/member/save")
        public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO)
    {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return "login";
    }

    @GetMapping("/member/sub")
    public String sub()
    {
        return "sub";
    }


    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult= memberService.login(memberDTO);
        if(loginResult != null){
            session.setAttribute("loginEmail",loginResult.getMemberEmail());
            if ("admin".equals(loginResult.getMemberEmail())) {
                return "redirect:/admin";
            } else {
                return "main";
            }
        } else {
            return "login";
        }
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
       String myEmail = (String)session.getAttribute("loginEmail");
       MemberDTO memberDTO =memberService.updateForm(myEmail);
       model.addAttribute("updateMember",memberDTO);
       return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/";
    }

    @GetMapping("/member/logout")
        public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }


    @GetMapping("/member/login")
        public String loginForm(){
        return "login";
    }

    @PostMapping("/member/email-check")
    public  @ResponseBody String emailCheck(@RequestParam("memberEmail")String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
    }
    @GetMapping("/member")
    public ResponseEntity<Boolean> checkDuplicate(@RequestParam("memberEmail")String memberEmail){
        boolean isUnique = memberService.isEmailUnique(memberEmail);
        return ResponseEntity.ok(isUnique);
    }


    // 관리자 페이지에 대한 디비
    // UserEntity 사용
    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<UserEntity> userList = userRepository.findAll(); // userRepository로 변경
        model.addAttribute("userList", userList);
        return "admin";
    }
}





