package pack.dto.booking;


import lombok.Data;

import java.io.Serializable;

@Data
public class bookingDTO implements Serializable {


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
}
