package com.github.renny.order.system.common.exception;

public class InvalidStatusTransitionException extends BaseException{
    public InvalidStatusTransitionException(String message){super(422,message);}
}
