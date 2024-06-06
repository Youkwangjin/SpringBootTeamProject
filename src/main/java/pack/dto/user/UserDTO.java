package pack.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userTel;
    private String userAddress;
    private LocalDate userCreated;
}
