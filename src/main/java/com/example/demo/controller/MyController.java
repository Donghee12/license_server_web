//package com.example.demo.controller;
//
//import com.example.demo.dataclass.MemberEntity;
//import com.example.demo.repository.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//
//
//@Controller
//public class MyController {
//    @Autowired
//    private MemberRepository memberRepository;
//
//
//
//    @GetMapping("/admin/licenses_info")
//    public String loadMainPage(Model model) {
//        List<MemberEntity> memberList = memberRepository.findAll();
//        model.addAttribute("members", memberList);
//        return "admin/licenses_info";
//    }
//
//
//}
