package smartcast.uz.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseMessage(Integer code, String message, List<ValidationFieldError> fields) {
}
