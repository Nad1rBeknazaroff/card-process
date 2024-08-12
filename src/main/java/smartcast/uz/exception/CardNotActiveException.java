package smartcast.uz.exception;

import lombok.RequiredArgsConstructor;
import smartcast.uz.enums.ErrorCode;

import static smartcast.uz.enums.ErrorCode.CARD_NOT_ACTIVE;

@RequiredArgsConstructor
public final class CardNotActiveException extends CardException {

    public CardNotActiveException(String message) {
        super(message);
    }

    @Override
    public ErrorCode errorCode() {
        return CARD_NOT_ACTIVE;
    }

    @Override
    public Object[] getErrorMessageArguments() {
        return new Object[]{getMessage()};
    }
}
