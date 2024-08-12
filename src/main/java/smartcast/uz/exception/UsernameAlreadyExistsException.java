package smartcast.uz.exception;

import jdk.jfr.Registered;
import smartcast.uz.enums.ErrorCode;

import static smartcast.uz.enums.ErrorCode.USERNAME_ALREADY_EXISTS;

@Registered
public final class UsernameAlreadyExistsException extends CardException{
    @Override
    public ErrorCode errorCode() {
        return USERNAME_ALREADY_EXISTS;
    }
}
