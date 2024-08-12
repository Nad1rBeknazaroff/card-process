package smartcast.uz.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import smartcast.uz.enums.ErrorCode;
import smartcast.uz.payload.response.BaseMessage;
import smartcast.uz.payload.response.ValidationFieldError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResourceBundleMessageSource errorMessageSource;

    @ExceptionHandler(CardException.class)
    public ResponseEntity<BaseMessage> handleCardException(CardException exception) {
        if (exception instanceof CardNotFoundException) return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getErrorMessage(errorMessageSource));
        else return ResponseEntity.badRequest()
                .body(exception.getErrorMessage(errorMessageSource));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ValidationFieldError> fieldErrorList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    String[] split = error.getDefaultMessage() != null ? error.getDefaultMessage().split("#") : new String[0];
                    String errorCode = split.length > 0 ? split[0] : ErrorCode.VALIDATION_ERROR.name();
                    String[] arguments = split.length > 1 ? Arrays.copyOfRange(split, 1, split.length) : null;
                    return new ValidationFieldError(
                            error.getField(),
                            getErrorMessage(errorCode, arguments, errorMessageSource)
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseMessage(
                        ErrorCode.VALIDATION_ERROR.getCode(),
                        getErrorMessage(ErrorCode.VALIDATION_ERROR.name(), null, errorMessageSource),
                        fieldErrorList
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseMessage> handleConstraintViolation(ConstraintViolationException ex) {
        List<ValidationFieldError> fieldErrorList = ex.getConstraintViolations().stream()
                .map(error -> {
                    String[] split = error.getMessage() != null ? error.getMessage().split("#") : new String[0];
                    String errorCode = split.length > 0 ? split[0] : ErrorCode.VALIDATION_ERROR.name();
                    String[] arguments = split.length > 1 ? Arrays.copyOfRange(split, 1, split.length) : null;
                    return new ValidationFieldError(
                            error.getPropertyPath().toString(),
                            getErrorMessage(errorCode, arguments, errorMessageSource)
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseMessage(
                        ErrorCode.VALIDATION_ERROR.getCode(),
                        getErrorMessage(ErrorCode.VALIDATION_ERROR.name(), null, errorMessageSource),
                        fieldErrorList
                ));
    }

    private String getErrorMessage(String errorCode, String[] errorMessageArguments, ResourceBundleMessageSource errorMessageSource) {
        try {
            return errorMessageSource.getMessage(errorCode, errorMessageArguments, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
