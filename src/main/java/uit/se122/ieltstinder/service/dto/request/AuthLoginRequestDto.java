package uit.se122.ieltstinder.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static uit.se122.ieltstinder.constant.MessageConstant.*;

@Data
public class AuthLoginRequestDto {

    @NotBlank(message = EMAIL_REQUIRED_ERROR)
    @Email(message = EMAIL_FORMAT_ERROR)
    private String email;

    @NotBlank(message = PASSWORD_REQUIRED_ERROR)
    private String password;

}
