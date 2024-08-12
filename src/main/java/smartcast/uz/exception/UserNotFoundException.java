package smartcast.uz.exception;

import lombok.RequiredArgsConstructor;
import smartcast.uz.enums.ErrorCode;

@RequiredArgsConstructor
public final class UserNotFoundException extends CardException{


    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode errorCode() {
        return ErrorCode.USER_NOT_FOUND;
    }
}
