package com.github.renny.order.system.modules.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserNameRequest {
    @Schema(
            description = "使用者名稱",
            example = "小包"
    )
    @NotBlank(message = "內容不得為空")
    @Size(min = 2,max = 20,message = "使用者名稱必須介於{min}到{max}之間")
    private String userName;

    public String getUserName(){ return userName; }
    public void setUserName(String userName){ this.userName = userName; }
}
