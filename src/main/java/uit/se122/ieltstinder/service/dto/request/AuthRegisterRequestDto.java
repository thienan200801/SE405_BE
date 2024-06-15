package uit.se122.ieltstinder.service.dto.request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

import static uit.se122.ieltstinder.constant.MessageConstant.*;

@Data
public class AuthRegisterRequestDto {

    @NotBlank(message = FIRST_NAME_REQUIRED_ERROR)
    @Length(max = 250, message = FIRST_NAME_MAX_LENGTH_ERROR)
    private String firstName;

    @NotBlank(message = LAST_NAME_REQUIRED_ERROR)
    @Length(max = 250, message = LAST_NAME_MAX_LENGTH_ERROR)
    private String lastName;

    @NotBlank(message = EMAIL_REQUIRED_ERROR)
    @Length(max = 250, message = EMAIL_MAX_LENGTH_ERROR)
    @Email(message = EMAIL_FORMAT_ERROR)
    private String email;

    @NotBlank(message = PASSWORD_REQUIRED_ERROR)
    @Length(max = 250, message = PASSWORD_MAX_LENGTH_ERROR)
    @Length(min = 8, message = PASSWORD_MIN_LENGTH_ERROR)
    private String password;

    @NotBlank(message = ADDRESS_REQUIRED_ERROR)
    @Length(max = 300, message = ADDRESS_MAX_LENGTH_ERROR)
    private String address;

    @Min(value = 0, message = GENDER_MIN_VALUE_ERROR)
    @Max(value = 1, message = GENDER_MAX_VALUE_ERROR)
    @NotNull(message = GENDER_REQUIRED_ERROR)
    private Integer gender;

    @NotNull(message = AGE_REQUIRED_ERROR)
    @Max(value = 100, message = AGE_MAX_VALUE_ERROR)
    @Min(value = 0, message = AGE_MIN_VALUE_ERROR)
    private Integer age;

}
