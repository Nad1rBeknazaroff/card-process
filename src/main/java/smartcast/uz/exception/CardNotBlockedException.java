package smartcast.uz.exception;

import lombok.RequiredArgsConstructor;
import smartcast.uz.enums.ErrorCode;

@RequiredArgsConstructor
public final class CardNotBlockedException extends CardException {

    public CardNotBlockedException(String message) {
        super(message);
    }

    @Override
    public ErrorCode errorCode() {
        return ErrorCode.CARD_NOT_BLOCKED;
    }

    @Override
    public Object[] getErrorMessageArguments() {
        return new Object[]{getMessage()};
    }
}
