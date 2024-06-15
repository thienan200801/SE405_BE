package uit.se122.ieltstinder.service.dto.request;

import lombok.Data;

@Data
public class UpdateUserProfile {
    private String firstName;
    private String lastName;
    private String address;
    private Integer gender;
    private Integer age;
}
