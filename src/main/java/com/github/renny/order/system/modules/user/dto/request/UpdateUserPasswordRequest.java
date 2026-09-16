package com.github.renny.order.system.modules.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateUserPasswordRequest {
    @Schema( description = "使用者密碼(舊)" )
    @NotBlank(message = "內容不得為空")
    private String oldPassword;
    @Schema( description = "使用者密碼(新)" )
    @NotBlank(message = "內容不得為空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,20}$",
            message = "密碼必須為 8~20 碼英文數字組合，且至少包含一個英文大寫、英文小寫及數字")
    private String newPassword;
    @Schema( description = "使用者密碼(新密碼二次確認)" )
    @NotBlank(message = "內容不得為空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,20}$",
            message = "密碼必須為 8~20 碼英文數字組合，且至少包含一個英文大寫、英文小寫及數字")
    private String checkNewPassword;

    public String getOldPassword(){ return oldPassword; }
    public String getNewPassword(){ return newPassword; }
    public String getCheckNewPassword(){ return checkNewPassword; }

    public void setOldPassword(String oldPassword){ this.oldPassword = oldPassword; }
    public void setNewPassword(String newPassword){ this.newPassword = newPassword; }
    public void setCheckNewPassword(String checkNewPassword){ this.checkNewPassword = checkNewPassword; }

}
