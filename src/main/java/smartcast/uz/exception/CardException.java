package smartcast.uz.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import smartcast.uz.enums.ErrorCode;
import smartcast.uz.payload.response.BaseMessage;

@RequiredArgsConstructor
public abstract sealed class CardException extends RuntimeException permits CardLimitExceededException, CardNotActiveException, CardNotBlockedException, CardNotFoundException, ETagMismatchException, InsufficientFundsException, UserNotFoundException, UsernameAlreadyExistsException {

    public CardException(String message) {
        super(message);
    }

    public abstract ErrorCode errorCode();

    public Object[] getErrorMessageArguments() {
        return null;
    }

    public BaseMessage getErrorMessage(ResourceBundleMessageSource errorMessageSource) {
        String errorMessage;
        try {
            errorMessage = errorMessageSource.getMessage(
                    errorCode().name(),
                    getErrorMessageArguments(),
                    LocaleContextHolder.getLocale()
            );
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
        return new BaseMessage(errorCode().getCode(), errorMessage, null);
    }
}



