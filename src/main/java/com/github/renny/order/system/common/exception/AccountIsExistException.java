package com.github.renny.order.system.common.exception;

public class AccountIsExistException extends BaseException{
    public AccountIsExistException(String message){
        super(401,message);
    }
}
