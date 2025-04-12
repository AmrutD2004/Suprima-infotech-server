    package com.example.attendance_api.controller;


    import com.example.attendance_api.dto.ApiResponse;
    import com.example.attendance_api.dto.LoginRequest;
    import com.example.attendance_api.dto.RegisterRequest;
    import com.example.attendance_api.model.User;
    import com.example.attendance_api.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import jakarta.validation.Valid;

    @RestController
    @RequestMapping("/api")
    public class AuthController {
        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
            try {
                User user = new User();
                user.setFullname(request.getFullname());
                user.setEmpId(request.getEmpId());
                user.setEmail(request.getEmail());
                user.setPassword(request.getPassword());

                User registeredUser = userService.registerUser(user);

                return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
            }
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
            try {
                User user = userService.loginUser(request.getEmail(), request.getPassword());

                return ResponseEntity.ok(new ApiResponse(true, "Login successful", user));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
            }
        }
    }
