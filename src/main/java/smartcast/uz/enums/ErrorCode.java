package smartcast.uz.enums;

public enum ErrorCode {

    USER_NOT_FOUND(1),
    CARD_LIMIT_EXCEEDED(2),
    CARD_NOT_FOUND(3),
    E_TAG_MISMATCH(4),
    CARD_NOT_ACTIVE(5),
    CARD_NOT_BLOCKED(6),
    INSUFFICIENT_FUNDS(7),
    USERNAME_ALREADY_EXISTS(8),
    VALIDATION_ERROR(100),
    CARD_INITIAL_AMOUNT_ERROR(101),
    EXTERNAL_ID_ERROR(102),
    WITHDRAW_AMOUNT_ERROR(103),
    PURPOSE_ERROR(104),
    NAME_LENGTH_ERROR(105),
    PASSWORD_LENGTH_ERROR(106),
    USERNAME_LENGTH_ERROR(107);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
