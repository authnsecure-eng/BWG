package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.user.UserResponse;
import com.pcmc.bwg.dto.user.UserStatusRequest;
import com.pcmc.bwg.dto.user.UserUpdateRequest;
import com.pcmc.bwg.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UserResponse> create(@RequestParam Long agencyId,
                                                @RequestParam String fullName,
                                                @RequestParam String mobileNo,
                                                @RequestParam String email,
                                                @RequestParam String aadhaarNo,
                                                @RequestParam String address,
                                                @RequestParam String pinCode,
                                                @RequestParam String password,
                                                @RequestParam(required = false) MultipartFile photo) {
        var user = userService.create(agencyId, fullName, mobileNo, email, aadhaarNo, address, pinCode, password, photo);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(@RequestParam(required = false) Long agencyId) {
        List<UserResponse> response = userService.findAll(agencyId).stream()
                .map(UserResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserResponse.from(userService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(UserResponse.from(userService.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponse> updateStatus(@PathVariable Long id,
                                                       @Valid @RequestBody UserStatusRequest request) {
        return ResponseEntity.ok(UserResponse.from(userService.updateStatus(id, request.getStatus())));
    }

    @PostMapping(value = "/{id}/photo", consumes = "multipart/form-data")
    public ResponseEntity<UserResponse> updatePhoto(@PathVariable Long id, @RequestParam MultipartFile photo) {
        return ResponseEntity.ok(UserResponse.from(userService.updatePhoto(id, photo)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
