package pack.dto.owner;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private String business_num;
    private String owner_pwd;
    private String owner_repwd;
    private String owner_name;
    private String owner_tel;
    private String email;
    private String cont_num;
}