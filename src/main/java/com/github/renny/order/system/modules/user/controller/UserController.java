package com.github.renny.order.system.modules.user.controller;

import com.github.renny.order.system.common.response.ApiResponse;
import com.github.renny.order.system.modules.user.dto.request.UpdateUserNameRequest;
import com.github.renny.order.system.modules.user.dto.request.UpdateUserPasswordRequest;
import com.github.renny.order.system.modules.user.dto.response.ReadUserProfileResponse;
import com.github.renny.order.system.modules.user.dto.response.UpdateUserNameResponse;
import com.github.renny.order.system.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "User",
        description = "使用者相關 API"
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    // TODO: token解析id未上
    @Operation(
            summary = "查看使用者資料",
            description = "此 API 用於查看現有使用者的 Email 與暱稱" ,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadUserProfileResponse>> readUserProfile(@PathVariable Long id){
        ReadUserProfileResponse response = userService.readUserProfile(id);
        return ResponseEntity.ok(ApiResponse.success("查看使用者資料",response));
    }

    @Operation(
            summary = "修改使用者名稱",
            description = "此 API 用於修改現有的使用者名稱",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/{id}/username")
    public ResponseEntity<ApiResponse<UpdateUserNameResponse>> updateUserName(@PathVariable Long id,
                                                                              @Valid @RequestBody UpdateUserNameRequest request){
        UpdateUserNameResponse response = userService.updateUserName(id, request);
        return ResponseEntity.ok(ApiResponse.success("成功更改使用者名稱",response));
    }

    @Operation(
            summary = "修改使用者密碼",
            description = "此 API 用於修改現有的使用者密碼",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiResponse<Void>> updateUserPassword(@PathVariable Long id,
                                                                @PathVariable String orderNum){
        userService.updateUserPassword(id,orderNum);
        return ResponseEntity.ok(ApiResponse.success("更改密碼成功,請重新登入",null));
    }

    @Operation(
            summary = "刪除使用者帳號",
            description = "此 API 用於刪除使用者帳號",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> softDeleteUserAccount(@PathVariable Long id){
        userService.softDeleteUserAccount(id);
        return ResponseEntity.ok(ApiResponse.success("帳戶刪除成功",null));
    }
}
