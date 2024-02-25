package pack.dto.contact;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private String contact_no;
    private String contact_name;
    private String contact_email;
    private String contact_title;
    private String contact_message;
    private String contact_date;
    private String contact_status;
}
