package pack.dto.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private String admin_id;
    private String admin_pwd;
    private String user_id, user_pwd, user_name, user_tel, user_email, user_addr, user_jumin,
            business_num, owner_pwd, owner_name, owner_tel, email, cont_num,
            cont_no, cont_addr, cont_we, cont_kyung, cont_size, cont_name, owner_phone, cont_status, cont_image, owner_num;
    private String searchValue, selectSearch, svalue;
}
