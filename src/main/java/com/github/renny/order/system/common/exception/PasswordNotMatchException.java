package com.github.renny.order.system.common.exception;

public class PasswordNotMatchException extends BaseException{
    public PasswordNotMatchException(String message){
        super(401,message);
    }
}
