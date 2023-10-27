package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyController {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LicenseInfoRepository licenseInfoRepository;

    @GetMapping("/members")
    public String listMembers(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/main")
    public String getLicenseInfo(Model model) {
        List<LicenseInfo> licenseInfoList = licenseInfoRepository.findAll();
        model.addAttribute("licenseInfoList", licenseInfoList);
        return "main"; // Thymeleaf 템플릿 이름
    }

}
