package com.github.renny.order.system.common.exception;

public class AccountNotExistException extends BaseException {
    public AccountNotExistException(String message){
        super(401,message);
    }
}
