package pack.dto.booking;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO implements Serializable {
    private int booking_id;
    private String user_id;
    private String user_name;
    private String user_tel;
    private String user_email;
    private String booking_date_start;
    private String booking_date_end;
    private String cont_no;
    private String cont_size;
    private String booking_price;
    private boolean success;
    private String message;
    private String booking_status;
}
