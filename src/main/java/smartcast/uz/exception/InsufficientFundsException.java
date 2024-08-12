package smartcast.uz.exception;

import lombok.RequiredArgsConstructor;
import smartcast.uz.enums.ErrorCode;

import static smartcast.uz.enums.ErrorCode.INSUFFICIENT_FUNDS;

@RequiredArgsConstructor
public final class InsufficientFundsException extends CardException{
    @Override
    public ErrorCode errorCode() {
        return INSUFFICIENT_FUNDS;
    }
}
