package com.example.demo.controller;

import com.example.demo.dataclass.LicenseInfo;
import com.example.demo.repository.LicenseInfoRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyController {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LicenseInfoRepository licenseInfoRepository;



    @GetMapping("/admin")
    public String loadMainPage(Model model) {
        List<LicenseInfo> licenseInfoList = licenseInfoRepository.findAll();
        model.addAttribute("licenseInfoList", licenseInfoList);
        return "admin";
    }


}
