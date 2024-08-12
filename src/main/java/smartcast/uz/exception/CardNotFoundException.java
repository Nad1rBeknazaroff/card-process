package smartcast.uz.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import smartcast.uz.enums.ErrorCode;

@RequiredArgsConstructor
@Setter
@Getter
public final class CardNotFoundException extends CardException {
    public CardNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorCode errorCode() {
        return ErrorCode.CARD_NOT_FOUND;
    }

    @Override
    public Object[] getErrorMessageArguments() {
        return new Object[]{getMessage()};
    }
}
