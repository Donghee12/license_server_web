package com.example.demo.controller;

import com.example.demo.LicenseInfo;
import com.example.demo.repository.LicenseInfoRepository;
import com.example.demo.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String loadMainPage(Model model) {
        List<LicenseInfo> licenseInfoList = licenseInfoRepository.findAll();
        model.addAttribute("licenseInfoList", licenseInfoList);
        return "main";
    }


}
