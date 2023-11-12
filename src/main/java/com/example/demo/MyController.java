package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members")
    public String listMembers(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/main") // 이 부분을 변경
    public String loadMainPage(Model model) {
        // ...
        return "main"; // main.html을 렌더링
    }

}
