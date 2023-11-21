package com.example.demo.controller;

import ch.qos.logback.core.model.Model;
import com.example.demo.dataclass.UserDTO;
import com.example.demo.dataclass.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private UserRepository userRepository;

    // 관리자 페이지에 대한 디비
    // UserEntity 사용
    @GetMapping("/admin/user_info")
    public String getAdminPage(org.springframework.ui.Model model) {
        List<UserEntity> userList = userRepository.findAll(); // userRepository로 변경
        model.addAttribute("userList", userList);
        return "admin/user_info";
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            // 사용자 정보를 가져오는 로직
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            // 예외가 발생한 경우에는 클라이언트에게 500 Internal Server Error와 함께 메시지를 전달
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    private UserDTO convertToDTO(UserEntity userEntity) {
        // UserEntity를 UserDTO로 변환하는 로직 작성
        // 필요한 정보만을 복사하여 UserDTO 객체를 생성하고 반환
        if (userEntity == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setMemberEmail(userEntity.getMemberEmail());
        userDTO.setRole(userEntity.getRole());
        userDTO.setStatus(userEntity.getStatus());

        return userDTO;
    }

}
