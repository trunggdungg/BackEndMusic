package com.example.musicbackend.rest;

import com.example.musicbackend.config.JwtUtil;
import com.example.musicbackend.entity.User;
import com.example.musicbackend.model.request.LoginRequest;
import com.example.musicbackend.model.request.RegisterRequest;
import com.example.musicbackend.model.respone.AuthResponse;
import com.example.musicbackend.model.respone.DTO.UserDTO;
import com.example.musicbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.register(registerRequest);
            String token = jwtUtil.generateToken(user.getId());

            AuthResponse authResponse = AuthResponse.builder()
                    .success(true)
                    .message("Đăng ký thành công")
                    .token(token)
                    .user(UserDTO.fromEntity(user))
                    .build();
            return ResponseEntity.ok(authResponse);
        }catch (Exception e) {
            AuthResponse response = AuthResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        Optional userOpt = userService.login(loginRequest);

        if (userOpt.isPresent()) {
            User user = (User) userOpt.get();
            String token = jwtUtil.generateToken(user.getId());

            AuthResponse authResponse = AuthResponse.builder()
                    .success(true)
                    .message("Đăng nhập thành công")
                    .token(token)
                    .user(UserDTO.fromEntity(user))
                    .build();
            return ResponseEntity.ok(authResponse);
        } else {
            AuthResponse response = AuthResponse.builder()
                    .success(false)
                    .message("Đăng nhập thất bại. Vui lòng kiểm tra lại email và mật khẩu.")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");

            if (jwtUtil.validateToken(token)) {
                Integer userId = jwtUtil.getUserIdFromToken(token);
                Optional userOpt = userService.findById(userId);

                if (userOpt.isPresent()) {
                    return ResponseEntity.ok(AuthResponse.builder()
                            .success(true)
                            .message("Token hợp lệ")
                            .user(UserDTO.fromEntity((User) userOpt.get()))
                            .build());
                }
            }

            return ResponseEntity.status(401).body("Token không hợp lệ");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token không hợp lệ");
        }
    }

}
