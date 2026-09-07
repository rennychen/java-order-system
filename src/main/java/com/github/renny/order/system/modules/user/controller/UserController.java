package com.github.renny.order.system.modules.user.controller;

import com.github.renny.order.system.common.response.ApiResponse;
import com.github.renny.order.system.modules.user.dto.request.UpdateUserNameRequest;
import com.github.renny.order.system.modules.user.dto.request.UpdateUserPasswordRequest;
import com.github.renny.order.system.modules.user.dto.response.ReadUserProfileResponse;
import com.github.renny.order.system.modules.user.dto.response.UpdateUserNameResponse;
import com.github.renny.order.system.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    // TODO: token解析id未上
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadUserProfileResponse>> readUserProfile(@PathVariable Long id){
        ReadUserProfileResponse response = userService.readUserProfile(id);
        return ResponseEntity.ok(ApiResponse.success("查看使用者資料",response));
    }

    @PatchMapping("/{id}/username")
    public ResponseEntity<ApiResponse<UpdateUserNameResponse>> updateUserName(@PathVariable Long id,
                                                                              @Valid @RequestBody UpdateUserNameRequest request){
        UpdateUserNameResponse response = userService.updateUserName(id, request);
        return ResponseEntity.ok(ApiResponse.success("成功更改使用者名稱",response));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiResponse<Void>> updateUserPassword(@PathVariable Long id,
                                                                @Valid @RequestBody UpdateUserPasswordRequest request){
        userService.updateUserPassword(id,request);
        return ResponseEntity.ok(ApiResponse.success("更改密碼成功,請重新登入",null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> softDeleteUserAccount(@PathVariable Long id){
        userService.softDeleteUserAccount(id);
        return ResponseEntity.ok(ApiResponse.success("帳戶刪除成功",null));
    }
}
