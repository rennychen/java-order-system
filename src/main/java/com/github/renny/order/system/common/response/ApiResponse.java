package com.github.renny.order.system.common.response;

public class ApiResponse<T> {
    private final boolean status;
    private final String message;
    private final T data;

    public ApiResponse(boolean success,String message,T data){
        this.status = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message,T data){
        return new ApiResponse<>(true,message,data);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>(false,message,null);
    }
}
