package smartcast.uz.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {
    private HttpStatus httpStatus;
    private T response;

    private Object header;
}
