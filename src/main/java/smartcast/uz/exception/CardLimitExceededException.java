package smartcast.uz.exception;

import lombok.RequiredArgsConstructor;
import smartcast.uz.enums.ErrorCode;

@RequiredArgsConstructor
public final class CardLimitExceededException extends CardException{
    @Override
    public ErrorCode errorCode() {
        return ErrorCode.CARD_LIMIT_EXCEEDED;
    }
}
