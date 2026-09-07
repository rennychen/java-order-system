package com.github.renny.order.system.modules.user.dto.response;

public class ReadUserProfileResponse {
    private String email;
    private String userName;

    public ReadUserProfileResponse(String email,String userName){
        this.email = email;
        this.userName = userName;
    }

    public String getEmail(){ return email; }
    public String getUserName(){ return userName; }
}
