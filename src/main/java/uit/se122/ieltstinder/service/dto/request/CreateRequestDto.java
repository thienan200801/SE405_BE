package uit.se122.ieltstinder.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static uit.se122.ieltstinder.constant.MessageConstant.TARGET_USER_ID_REQUIRED_ERROR;

@Data
public class CreateRequestDto {

    @NotNull(message = TARGET_USER_ID_REQUIRED_ERROR)
    private Long targetUserId;

}
