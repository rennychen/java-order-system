package com.github.renny.order.system.common.exception;

public class BusinessException extends BaseException{
    public BusinessException(String message){
        super(400,message);
    }
}
