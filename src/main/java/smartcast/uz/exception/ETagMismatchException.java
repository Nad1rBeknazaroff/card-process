package smartcast.uz.exception;

import lombok.RequiredArgsConstructor;
import smartcast.uz.enums.ErrorCode;

@RequiredArgsConstructor
public final class ETagMismatchException extends CardException{
    @Override
    public ErrorCode errorCode() {
        return ErrorCode.E_TAG_MISMATCH;
    }
}
