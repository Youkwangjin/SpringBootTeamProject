package pack.dto.container;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ContainerDTO {
	private int cont_price;
	private String cont_addr;
	private String cont_size;
	private String cont_image;
	private String cont_status;
	private String owner_name;
	private String owner_num;
	private String business_num;
	private String cont_no;
	private String cont_explain;
	private String owner_phone;
	private String cont_name;
	private double cont_we;
	private double cont_kyung;
	private String cont_area;
}
