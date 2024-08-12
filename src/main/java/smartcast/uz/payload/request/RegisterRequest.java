package smartcast.uz.payload.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Size(min = 6, message = "USERNAME_LENGTH_ERROR")
    private String username;
    @Size(min = 5, message = "NAME_LENGTH_ERROR")
    private String name;
    @Size(min = 8, message = "PASSWORD_LENGTH_ERROR")
    private String password;
}
