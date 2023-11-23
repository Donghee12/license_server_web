package com.example.demo.controller;

import ch.qos.logback.core.model.Model;
import com.example.demo.dataclass.UserDTO;
import com.example.demo.dataclass.UserEntity;
import com.example.demo.dataclass.UserUpdateRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 관리자 페이지에 대한 디비
    // UserEntity 사용
    @GetMapping("/admin/user_info")
    public String getAdminPage(org.springframework.ui.Model model) {
        List<UserEntity> userList = userRepository.findAll(); // userRepository로 변경
        model.addAttribute("userList", userList);
        return "admin/user_info";
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }
    @Transactional
    @RequestMapping(value = "/update/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();

            // 중복 코드를 제거하고 변경할 필드만 업데이트
            updateFieldIfNotNull(userUpdateRequest.getUsername(), user::setName);
            updateFieldIfNotNull(userUpdateRequest.getRole(), user::setRole);
            updateFieldIfNotNull(userUpdateRequest.getStatus(), user::setStatus);

            userRepository.save(user);

            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    private <T> void updateFieldIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
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
